from llm_client import HelloAgentsLLM
from PlanSolve_run_agent import Planner, Executor
class PlanAndSolveAgent:
    def __init__(self, llm_client):
        self.planner = Planner(llm_client)
        self.executor = Executor(llm_client)
    def run(self, question):
        plan = self.planner.plan(question)
        if not plan:
            print("计划生成失败")
            return
        answer = self.executor.execute(question, plan)
        print("最终答案:", answer)
if __name__ == "__main__":
    llm_client = HelloAgentsLLM()
    agent = PlanAndSolveAgent(llm_client)

    agent.run("小明第一天有15个苹果,第二天的苹果数量是第一天的两倍,第三天比第二天少5个,一共有多少个苹果?")