import torch
import torch.nn as nn#导入PyTorch中的神经网络模块
import math#导入数学库
"""为输入的token添加位置信息"""
class PositionalEncoding(nn.Module):#nn.Module：PyTorch中所有神经网络模块的基础类
    """位置编码"""
    def __init__(self,d_model:int,dropout:float=0.1,max_len:int=5000):
        super().__init__()
        self.dropout=nn.Dropout(p=dropout)#Dropout 的作用是：训练时随机将一部分神经网络中的数值置为 0，降低模型对某些特征的过度依赖，帮助减少过拟合
        position=torch.arange(max_len).unsqueeze(1)#在第 1 个维度增加一个维度
        div_term = torch.exp(torch.arange(0, d_model, 2) * (-math.log(10000.0) / d_model))#为不同的特征维度生成不同的频率系数。
        #创建位置编码矩阵
        pe = torch.zeros(max_len, d_model)
        pe[:, 0::2] = torch.sin(position * div_term)#为每个位置的偶数维度生成正弦位置编码
        pe[:, 1::2] = torch.cos(position * div_term)#为每个位置的奇数维度生正弦位置编码
        self.register_buffer('pe', pe.unsqueeze(0))
    def forward(self,x):#forward:规定神经网络前向传播的计算过程
        x = x + self.pe[:, :x.size(1)]
        return self.dropout(x)
"""模型需要判断当前token应该重点关注输入中的哪些其他tokens
attention 就是帮助模型计算这些 Token 之间的关联程度
换句话说就是让模型在处理一个 Token 时，能够关注输入序列中其他相关的 Token,从而理解它们之间的关系"""
class MultiHeadAttention(nn.Module):
    """多头注意力机制模块"""
    def __init__(self,d_model,num_heads):
        super(MultiHeadAttention,self).__init__()
        assert d_model % num_heads==0,"d_model必须能被num_heads整除"
        self.d_model=d_model
        self.num_heads=num_heads
        self.d_k=d_model//num_heads
        self.w_q=nn.Linear(d_model,d_model)
        self.w_k=nn.Linear(d_model,d_model)
        self.w_v=nn.Linear(d_model,d_model)
        self.w_o=nn.Linear(d_model,d_model)
    def scaled_dot_product_attention(self,Q,K,V,maskj=None):
        attn_scores=torch.matmul(Q,K.transpose(-2,-1))/math.sqrt(self.d_k)
        if mask is not None:
            attn_scores=attn_scores.masked_fill(mask==0,-le9)
        attn_probs=torch.softmax(attn_scores,dim=-1)
        output=torch.matmul(attn_probs,V)
        return output
    def split_heads(self,x):
        batch_size,seq_length,d_model=x.size()
        return x.view(batch_size,seq_length,self.num_heads,self.d_k).transpose(1,2)
    def combine_heads(self, x):
        batch_size, num_heads, seq_length, d_k = x.size()
        return x.transpose(1, 2).contiguous().view(batch_size, seq_length, self.d_model)
    def forward(self,Q,K,V,mask=None):#self:当前对象;query:查询向量;key:键向量;value:值向量;mask:掩码;
        Q=self.split_heads(self.w_q(Q))
        K=self.split_heads(self.w_k(K))
        V=self.split_heads(self.w_v(V))
        attn_output = self.scaled_dot_product_attention(Q, K, V, mask)
        output = self.W_o(self.combine_heads(attn_output))
        return output
"""这个网络会对每一个 Token 的向量分别进行相同的计算,
进一步处理每个 Token 自身的特征。"""
class PositionWiseFeedForward(nn.Module):
    """位置前馈网络模块"""
    def __init__(self,d_model,d_ff,dropout=0.1):
        super(PositionWiseFeedForward,self).__init__()
        self.linear1=nn.Linear(d_model,d_ff)
        self.dropout=nn.Dropout(dropout)
        self.linear2 = nn.Linear(d_ff, d_model)
        self.relu=nn.ReLU()
    def forward(self,x):
        x = self.linear1(x)
        x = self.relu(x)
        x = self.dropout(x)
        x = self.linear2(x)
        return x
class EncoderLayer(nn.Module):
    def __init__(self,d_model,num_heads,d_ff,dropout):#d_model:token向量维度；num_heads：注意力头数量；d_ff：前馈网络隐藏层维度；dropout：Dropout比例
        #定义四个组件
        super(EncoderLayer,self).__init__()
        self.self_attn=MultiHeadAttention()#自注意力机制
        self.feed_forward=PositionWiseFeedForward()#前馈神经网络
        self.norm1=nn.LayerNorm(d_model)#第一层归一化
        self.norm2=nn.LayerNorm(d_model)#第二层归一化
        self.dropout=nn.Dropout(dropout)#减少过拟合
    def forward(self,x,mask):
        attn_output=self.self_attn(x,x,x,mask)
        x=self.normal(x+self.dropout(attn_output))#我们希望网络在学习新特征的同时，保留原始输入的信息,这就是残差连接
        ff_output=self.feed_forward(x)
        x=self.norm2(x+self.dropout(ff_output))
        return x
class DecoderLayer(nn.Module):
    def __init__(self,d_model,num_heads,d_ff,dropout):
        super(DecoderLayer,self).__init__()
        self.self_attn=MultiHeadAttention()
        self.cross_attn=MultiHeadAttention()#创建交叉注意力模块，这是encoder,decoder之间进行信息交互的地方
        self.feed_forward=PositionWiseFeedForward()
        self.norm1=nn.LayerNorm(d_model)
        self.norm2=nn.LayerNorm(d_model)
        self.norm3=nn.LayerNorm(d_model)
        self.dropout=nn.Dropout(dropout)
    def forward(self,x,encoder_output,src_mask,tgt_mask):
        attn_output=self.self_attn(x,x,x,tgt_mask)
        x=self.normal(x+self.dropout(attn_output))
        cross_attn_output=self.cross_attn(x,encoder_output,encoder_output(src_mask))
        x=self.norm2(x+self.dropout(cross_attn_output))
        ff_output=self.feed_forward(x)
        x=self.norm3(x+self.dropout(ff_output))
        return x