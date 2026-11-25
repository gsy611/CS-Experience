from Reflection_agent import ReflectionAgent
from llm_client import HelloAgentsLLM
llm=HelloAgentsLLM()
agent=ReflectionAgent(
    llm,
    max_iterations=3
)
task="""
请写一个python函数
判断一个数字是否为质数。
要求：
时间复杂度尽可能低。
"""
agent.run(task)