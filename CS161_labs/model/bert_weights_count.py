#!/usr/bin/env python
# -*- coding: utf-8 -*-
"""
@file: 
@version: 
@desc:  
@author: wangfc
@site: 
@time: 2021/1/2 9:55 

@Modify Time      @Author    @Version    @Desciption
------------      -------    --------    -----------
2021/1/2 9:55   wangfc      1.0         None

 * 密级：秘密
 * 版权所有：******股份有限公司 2019
 * 注意：本内容仅限于******股份有限公司内部传阅，禁止外泄以及用于其他的商业目的

"""

"bert_base"
"embedding 层"
block_nums= 12
multiheads_nums=12

vocab = 30522 # 中文 21128
hidden_size = 768
position_tokens = 512
segment_tokens =2

# Token Embeddings + Segment Embeddings + Position Embeddings
tokens_embedding_weight = vocab* hidden_size
position_tokens_embedding_weight = position_tokens * hidden_size
segment_tokens_embedding_weight =segment_tokens *hidden_size
embedding_weight =tokens_embedding_weight + position_tokens_embedding_weight
# = 23440896 + 393216 + 1536 = 23835136

# embedding之后做了 Layer Normalization：用layer_norm做归一化（因为embedding向量按位求和（element-wise add）改变了向量各维值的scale
# 对应的是center和scale。
layernorm_weight = hidden_size*2
embedding_block_weight = embedding_weight +layernorm_weight

# encoder = encoder_block * 12
"self_attention_block"
# multi-head因为分成12份
self_attention_input_size = hidden_size/12
self_attention_output_size = hidden_size
# W_K,W_Q,W_V的参数,每个都加了一个bias，bias的参数量是768/12=64
self_attention_weight = (self_attention_input_size*self_attention_output_size+ self_attention_output_size)*3

# 紧接着将多个head进行concat再进行变换，此时W的大小是768 * 768,又加了个768个参数的bias；
self_attention_transform_weight = hidden_size*hidden_size + hidden_size
# 590592
# norm使用的是layer normalization，每个维度有两个参数
layernorm_weight = hidden_size *2

self_attention_block = self_attention_weight *multiheads_nums + self_attention_transform_weight+ layernorm_weight
# 1797120 +589824+  1536 =  2388480

"ffblock"
ffblock_input_size = hidden_size
# 第一层：原文中4H长度
ffblok_first_layer_output_size = hidden_size*4
ffblock_first_layer_weight = ffblok_first_layer_output_size *ffblock_input_size + ffblok_first_layer_output_size
# 2362368
# 第二层：
ffblock_output_size = hidden_size
ffblock_second_layer_weight = ffblok_first_layer_output_size * ffblock_output_size + ffblock_output_size
# 2360064
layernorm_weight = hidden_size *2
ffblock_weight  = ffblock_first_layer_weight + ffblock_second_layer_weight +layernorm_weight
# 2362368 + 2360064 + 1536 = 4722432‬ +1536 = 4723968

"encoder_block = self_attention_block +ffblock "
encoder_weight = self_attention_block*block_nums +ffblock_weight*block_nums
# 28661760 + 56687616 =85349376

"pooler"
pooler_weight= hidden_size*hidden_size +hidden_size
"""
在句子级的任务中，会需要对每个序列有一个向量表示，于是就有了pooled_output，
它来自于最后一层Transformer对序列的首个token（即[CLS]）的输出，经过一层全连接网络而得到。
这个操作之所以叫"pooler", 跟avg-pooling, max-pooling这些pooling操作并没有关系，
而是指得到的pooled_output是对整个序列中所有token信息的汇集结果。
"""

bert_base_weights = embedding_block_weight + encoder_weight + pooler_weight
# 23837184 + 85349376 + 590592 = 109777152
#  Token Embeddings + Segment Embeddings + Position Embeddings + layernorm + (self_attention + ff)*12 +  pooler_weight
#  23440896 + 393216 + 1536 + 1536 + 28661760 + 56687616 + 590592


"""
BERT的主要参数在于Token Embedding以及encoder里的feed-forward，multi-head self attention。
而multi-head self attention的参数量居然还没feed-forward多。

后来的ALBERT就针对Token Embedding采用了类似推荐系统里矩阵分解的 factorized embedding，
以及针对encoder的cross-layer parameter sharing这两种方法来改进，从而大大减少参数量。

albert-base 12M parameters
"""
"Factorized Embedding Parameterization"
embedding_size = 128
# 在词表 V 到 隐层 H 的中间，插入一个小维度 E，多做一次尺度变换
factorized_embedding_weight = embedding_size*hidden_size
tokens_embedding_weight_albert = vocab* embedding_size
position_tokens_embedding_weight_albert = position_tokens*embedding_size
segment_tokens_embedding_weight_albert = segment_tokens*embedding_size

embedding_block_weight_albert = tokens_embedding_weight_albert+ position_tokens_embedding_weight_albert+ segment_tokens_embedding_weight_albert\
                               + factorized_embedding_weight + layernorm_weight
# 23837184 ---> 4072448
"Cross-layer Parameter Sharing"
# 具体分为三种模式：只共享 attention 相关参数、只共享 FFN 相关参数、共享所有参数。
encoder_weight_albert = self_attention_block + ffblock_weight
# 85349376 --> 7112448

albert_base_weights = embedding_block_weight_albert + encoder_weight_albert + pooler_weight
# 109777152 ---> 11775488


