from collections import defaultdict

class TrieNode:
    def __init__(self):
        # 记录该节点的所有子节点： 字典的形式
        # defaultdict 接受一个工厂函数作为参数,factory_function 可以是list、set、str等等，
        # 作用是当key不存在时，返回的是工厂函数的默认值，比如list对应[ ]，str对应的是空字符串，set对应set( )，int对应
        self.children = defaultdict(TrieNode)

        self.children_keys = set()
        # 标记该节点是否为一个词的结束位置
        self.is_word = False
    
    def __repr__(self):
        return f"TrieNode(children_keys={self.children_keys}, is_word={self.is_word})"

class TrieTree():
    """
    208. 实现 Trie (前缀树)
    实现一个 Trie (前缀树)，包含 insert, search, 和 startsWith 这三个操作。

    示例:
    Trie trie = new Trie();

    trie.insert("apple");
    trie.search("apple");   // 返回 true
    trie.search("app");     // 返回 false
    trie.startsWith("app"); // 返回 true
    trie.insert("app");
    trie.search("app");     // 返回 true
    说明:

    你可以假设所有的输入都是由小写字母 a-z 构成的。
    保证所有输入均为非空字符串

    来源：力扣（LeetCode）
    链接：https://leetcode-cn.com/problems/implement-trie-prefix-tree
    著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
    """

    def __init__(self):
        """
        Initialize your data structure here.
        """
        self.root = TrieNode()


    def insert(self, word: str) -> None:
        """
        Inserts a word into the trie.
        """
        # 获取根节点
        current_node = self.root
        # 遍历所有的字符
        for w in word:
            # 判断当前节点的所有children 中是否有 w 的key， 必须使用 [] 来取值
            child_node = current_node.children[w]
            current_node.children_keys.add(w)
            current_node =child_node
            # 如果存在该 key,则获取对应的node，继续向下迭代
            # 如果不存在该 key，则会自动生成 该 key ---> node,因为是 defaultdict(factory_function) 类型，然后继续向下迭代，

        # 迭代完成的时候，标记该节点为一个词
        current_node.is_word = True


    def search(self, word: str) -> bool:
        """
        Returns if the word is in the trie.
        """
        # 获取 root 节点为当前节点
        current_node = self.root
        # 对word 的每个字符做迭代判断
        # 如果直到迭代完成的时候的那个节点的word为true,则说明该 word 在这个 tire 树中
        for w in word:
            # 如果 w 不在 当前节点的 children当中
            if w not in current_node.children:
                return False
            current_node = current_node.children[w]
        # 判断最后的节点的word
        # 迭代完成的时候，标记该节点为一个词
        if current_node.is_word == True:
            return True
        else:
            return False


    def startWith(self, prefix: str) -> list[str]:
        """
        Returns list words in the trie that starts with the given prefix.
        """
        current_node = self.root
        for w in prefix:
           if w not in current_node.children:
                return []
           current_node = current_node.children[w]
        
           # Helper function to recursively collect all words from the current node
        def dfs(node, current_suffix):
            words = []
            if node.is_word:
                words.append(current_suffix)
            for char, child_node in node.children.items():
                words.extend(dfs(child_node, current_suffix + char))
            return words

        # Collect all words that start with the prefix
        suffixes = dfs(current_node, '')

        # Prepend the prefix to each suffix to form the complete words
        return [prefix + suffix for suffix in suffixes]

  
    
if __name__ == '__main__':

    # d = defaultdict(list)
    # d["a"] =1
    # # 当 "b" 不存在字典当中的时候，默认会生成一个 list： "b" ---> list
    # print(d['b'])

    # tire_node = TrieNode()
    # tire_node.children['a'].__class__
    # tire_node.is_word


    # Your Trie object will be instantiated and called as such:
    obj = TrieTree()
    word = "apple"
    obj.insert(word)
    param_1 = obj.search(word)

    obj.insert("angle")
    obj.insert("app")
    
    # param_2 = obj.search(word)
    # param_2 = obj.search("app")
    # obj.search("angle")
    # param_3 = obj.startsWith("app")
    result = obj.startWith("app")
    print(result)

