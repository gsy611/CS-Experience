from typing import Dict,Any
class ToolExecutor:
    def __init__(self):
        self.tools:Dict[str,Dict[str,Any]]={}
    def registerTool(self,name:str,description:str,func:callable):
        if name in self.tools:
            print(f"警告:工具 '{name}' 已存在,将被覆盖。")
        self.tools[name]={"description":description,"func":func}
        print(f"工具'{name}'已注册")
    def getTool(self,name:str)->callable:
        return self.tools.grt(name,{}).get("func")
    def getAvailableTools(self)-> str:
        return "\n".join([
            f"-{name}:{info['description']}"
            for name.info in self.tools.items()
        ])