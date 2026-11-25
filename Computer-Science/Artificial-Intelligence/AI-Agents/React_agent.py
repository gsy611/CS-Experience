import re
from llm_client import HelloAgentsLLM
from tools.executor import ToolExecutor
# 1. ReAct 提示词模板
REACT_PROMPT_TEMPLATE = """
请注意，你是一个有能力调用外部工具的智能助手。

可用工具如下:
{tools}

请严格按照以下格式进行回应:

Thought: 你的思考过程，用于分析问题、拆解任务和规划下一步行动。

Action: 你决定采取的行动，必须是以下格式之一:
- {tool_name}[{tool_input}]: 调用一个可用工具。
- Finish[最终答案]: 当你认为已经获得最终答案时。

当你收集到足够的信息，能够回答用户的最终问题时，
你必须使用 Finish[最终答案] 输出最终答案。

现在，请开始解决以下问题:
Question: {question}

History: {history}
"""

class ReActAgent:
    # 2. 初始化 ReActAgent
    def __init__(
        self,
        llm_client: HelloAgentsLLM,
        tool_executor: ToolExecutor,
        max_steps: int = 5
    ):
        self.llm_client = llm_client
        self.tool_executor = tool_executor
        self.max_steps = max_steps
        self.history = []
    # 3. 运行 ReAct Agent
    def run(self, question: str):
        # 每次运行时重置历史记录
        self.history = []
        current_step = 0
        # 只要没有超过最大步数，就继续循环
        while current_step < self.max_steps:
            current_step += 1
            print(f"\n--- 第 {current_step} 步 ---")
            # Step 1: 格式化提示词
            tools_desc = self.tool_executor.getAvailableTools()
            history_str = "\n".join(self.history)
            prompt = REACT_PROMPT_TEMPLATE.format(
                tools=tools_desc,
                question=question,
                history=history_str
            )
            # Step 2: 调用 LLM 进行思考
            messages = [
                {"role": "user", "content": prompt}
            ]
            response_text = self.llm_client.think(
                messages=messages
            )
            if not response_text:
                print("错误:LLM 未能返回有效响应。")
                break
            # Step 3: 解析 LLM 的输出
            thought, action = self._parse_output(response_text)
            if thought:
                print(f"思考：{thought}")
            if not action:
                print("警告：未能解析出有效的 Action,流程终止。")
                break
            # Step 4: 执行 Action
            # 如果 LLM 决定结束任务
            if action.startswith("Finish"):
                match = re.fullmatch(r"Finish\[(.*)\]", action, re.DOTALL)
                if not match:
                    print("错误:Finish 格式不正确。")
                    break
                final_answer = match.group(1).strip()
                print(f"最终答案：{final_answer}")
                return final_answer
            # 解析普通工具调用
            tool_name, tool_input = self._parse_action(action)
            if not tool_name or tool_input is None:
                print("错误:Action 格式不正确。")
                break
            print(f"行动：{tool_name}[{tool_input}]")
            # 根据工具名称获取对应的 Python 函数
            tool_function = self.tool_executor.getTool(tool_name)
            if not tool_function:
                observation = f"错误：未找到名为 '{tool_name}' 的工具。"
            else:
                # 真正执行工具函数
                try:
                    observation = tool_function(tool_input)
                except Exception as e:
                    observation = f"工具执行发生错误：{e}"
            # Step 5: 整合 Observation
            print(f"观察：{observation}")
            self.history.append(f"Action: {action}")
            self.history.append(f"Observation: {observation}")
        # 超过最大步数，任务仍未完成
        print("已达到最大步数，流程终止。")
        return None
    # 4. 解析 LLM 的完整输出
    def _parse_output(self, text: str):
        thought_match = re.search(
            r"Thought:\s*(.*?)(?=\nAction:|$)",
            text,
            re.DOTALL
        )
        action_match = re.search(
            r"Action:\s*(.*?)$",
            text,
            re.DOTALL
        )
        thought = (
            thought_match.group(1).strip()
            if thought_match else None
        )
        action = (
            action_match.group(1).strip()
            if action_match else None
        )
        return thought, action
    # 5. 解析 Action 字符串
    def _parse_action(self, action_text: str):
        match = re.fullmatch(
            r"(\w+)\[(.*)\]",
            action_text,
            re.DOTALL
        )
        if match:
            return match.group(1), match.group(2)
        return None, None
