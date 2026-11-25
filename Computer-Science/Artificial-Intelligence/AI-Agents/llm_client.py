import os#他提供了与操作系统交互的功能
from openai import OpenAI#创建了一个API客户端，让Python可以向大语言模型服务发送请求
from dotenv import load_dotenv#读取项目中的.env文件
from typing import List,Dict
load_dotenv()#他会读取当前工作目录附近的.env文件，并将其中的配置加载到环境变量中
"""实现一个LLM客户端类"""
class HelloAgentLLM:
    def __init__(
            self,
            model:str=None,
            apiKey:str=None,
            baseUrl:str=None,
            timeout:int=None
    ):
        #设置模型配置信息
        self.model=model or os.getenv("LLM_MODEL_ID")#优先使用用户手动传入的参数，如果没有传入，就从 .env 文件中读取。
        apiKey=apiKey or os.getenv("LLM_API_KEY")
        baseUrl=baseUrl or os.getenv("LLM_BASE_URL")
        timeout=timeout or int(os.getenv("LLM_TIMEOUT",60))
        if not all([self.model,apiKey,baseUrl]):
            raise ValueError("模型ID,API密钥和服务地址必须被提供或在.env文件中定义")#raise 用于主动抛出异常。
        #创建OpenAI客户端
        self.client=OpenAI(
            api_key=apiKey,
            base_url=baseUrl,
            timeout=timeout
        )
    """接收对话内容 → 调用大语言模型 → 接收模型响应 → 返回完整文本"""
    def think(
            self,
            messages:List[Dict[str,str]],#表示传入的对话消息是一个列表，里面每个元素都是字典。
            temperature:float=0
        )-> str | None:
            print(f"正在调用{self.model}模型...")
            try:#异常处理
                response=self.client.chat.completions.create(#.chat.completions.create():这是 OpenAI SDK 提供的聊天补全接口。
                    model=self.model,
                    messages=messages,
                    temperature=temperature,
                    stream=True#开启流式响应。
                )
                print("大语言模型响应成功:")
                collected_content=[]
                for chunk in response:
                    content=chunk.choices[0].delta.content or ""
                    print(content,end="",flush=True)
                    collected_content.append(content)
                print()
                return "".join(collected_content)
            except Exception as e:
                print(f"调用LLM ApI 时发生错误:{e}")
                return None
if __name__=="__main__":
    try:
        llmClient = HelloAgentLLM()
        exampleMessages = [
            {
                "role": "system",
                "content": "You are a helpful assistant that writes Python code."
            },
            {
                "role": "user",
                "content": "写一个快速排序算法"
            }
        ]
        print("--- 调用 LLM ---")
        responseText = llmClient.think(exampleMessages)
        if responseText:
            print("\n--- 完整模型响应 ---")
            print(responseText)
    except ValueError as e:
        print(e)
