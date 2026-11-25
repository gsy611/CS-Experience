from serpapi import SerpApiClient
def search(query:str)->str:
    print(f"正在执行[SerpApi]网页搜索:{query}")
    try:
        api_key=os.getenv("SERPAPI_API_KEY")
        if not api_key:
            return "错误:SERPAPI_API_KEY 未在 .env 文件中配置"
        params={
            "engine":"google",
            "q":query,
            "api_key":api_key,
            "gl":"cn",#国家代码
            "hl":"zh-cn",#语言代码
        }
        client=SerpApiClient(params)
        results=client.get_dict()
        if "answer_box_list" in results:
            return "\n".join(results["answer_box_list"])
        if "answer_box" in results and "answer" in results["answer_box"]:
            return results["answer_box"]["answer"]
        if "knowledge_graph" in results and "description" in results["knowledge_graph"]:
            return results["knowledge_graph"]["description"]
        if "organic_results" in results and results["organic_results"]:
            snippets=[
                f"[{i+1}] {res.get('title','')}\n{res.get('snippet', '')}"
                for i,res in enumerate(results["organic_results"][:3])
            ]
            return "\n\n".join(snippets)
        return f"对不起，没有找到关于'{query}' 的信息"
    except Exception as e:
        return f"搜索时发生错误: {e}"