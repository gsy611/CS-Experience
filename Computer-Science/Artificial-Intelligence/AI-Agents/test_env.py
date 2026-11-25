import os
from dotenv import load_dotenv

# 加载 .env 文件
load_dotenv()

# 获取环境变量
api_key = os.getenv("LLM_API_KEY")
model_id = os.getenv("LLM_MODEL_ID")
base_url = os.getenv("LLM_BASE_URL")

# 检查配置
print("API Key loaded:", api_key is not None)
print("Model ID:", model_id)
print("Base URL:", base_url)