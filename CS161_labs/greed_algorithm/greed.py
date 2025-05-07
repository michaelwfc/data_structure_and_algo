#!/usr/bin/env python
# -*- coding: utf-8 -*-
"""
@file: 
@version: 
@desc:  
@author: wangfc
@site: http://www.hundsun.com
@time: 2021/1/18 23:08 

@Modify Time      @Author    @Version    @Desciption
------------      -------    --------    -----------
2021/1/18 23:08   wangfc      1.0         None

 * 密级：秘密
 * 版权所有：恒生电子股份有限公司 2019
 * 注意：本内容仅限于恒生电子股份有限公司内部传阅，禁止外泄以及用于其他的商业目的

"""
from typing import List
import heapq as hq

"""
1353. 最多可以参加的会议数目
给你一个数组 events，其中 events[i] = [startDayi, endDayi] ，表示会议 i 开始于 startDayi ，结束于 endDayi 。
你可以在满足 startDayi <= d <= endDayi 中的任意一天 d 参加会议 i 。注意，一天只能参加一个会议。
请你返回你可以参加的 最大 会议数目。

示例 1：
输入：events = [[1,2],[2,3],[3,4]]
输出：3
解释：你可以参加所有的三个会议。
安排会议的一种方案如上图。
第 1 天参加第一个会议。
第 2 天参加第二个会议。
第 3 天参加第三个会议。
示例 2：
输入：events= [[1,2],[2,3],[3,4],[1,2]]
输出：4

示例 3：
输入：events = [[1,4],[4,4],[2,2],[3,4],[1,1]]
输出：4

示例 4：
输入：events = [[1,100000]]
输出：1

示例 5：
输入：events = [[1,1],[1,2],[1,3],[1,4],[1,5],[1,6],[1,7]]
输出：7
 

"""
def attendMostMeeting(events:List[List[int]]):
    """
    @author:wangfc27441
    @desc: 
    思路： 
    根据所有events 开始位置 start_day，找到最小的值， 更新 schedule 的 start
    根据所有 events 结束位置 end_day，找到最小的值， 更新 schedule 的 end
    此时的end 对应的 event 应该本 schedule 中发生， 因此 meet_num +=1
    从 events 中去除该 end 作为结束日期的所有会议
    对剩下的 events 做循环处理，直到 events为空

    时间复杂度： 迭代次数 * 求最小值
    n * n*log(n)

    @version：
    @time:2021/2/24 22:05 
    
    Parameters
    ----------
    
    Returns
    -------
    """
    unarangemented_events = events.copy()
    meeting_num= 0
    arangmented_events = []
    while unarangemented_events.__len__()>0:
        min_start_day = min([start_day for start_day,end_day in unarangemented_events])
        min_end_day = min([end_day for start_day,end_day in unarangemented_events])

        for unarangemented_event in unarangemented_events.copy():
            # 遇到相同结束日期的时候，需要判断该结束日期前已经排了多少个会议
            # 如果已经排的会议个数 < 结束日期,则可以 继续安排会议
            # 否则说明已经排满
            if unarangemented_event[-1]==min_end_day:
                if arangmented_events.__len__() < min_end_day:
                    arangmented_events.append(unarangemented_event)
                    meeting_num += 1
                    print(
                        f"第 {meeting_num} 次meeting,min_start_day={min_start_day},min_end_day={min_end_day} arangmented_events={arangmented_events}")
                unarangemented_events.remove(unarangemented_event)

    return meeting_num


def attendMostMeetingV2(events:List[List[int]]):
    pass
    """
    @author:wangfc27441
    @desc: 
    既然我们每次迭代的时候只需要计算 end_day 最小值，用来判断安排哪个 event
    因此，可以把所有的 events 存储在 heap结构当中，key = end_day,value=event
    3 operations:
    1) insert :  O(log n)
    1) extractMin :  O(log(n))
    2) dropKey : 
    
    时间复杂度：
    建立 heap ： n * logn
    循环： n*logn * logn
    
    @version：
    @time:2021/2/24 22:34 
    
    Parameters
    ----------
    
    Returns
    -------
    """
    events_heap = EventsHeap(events=events)
    last_min_end_day = -1
    meeting_num = 0
    arangmented_events = []
    while events_heap.size()>0:
        event = events_heap.extract_min()
        start_day,min_end_day = event
        if last_min_end_day < min_end_day  or arangmented_events.__len__() < min_end_day:
            # 当上次会议的结束时间 < 当前事件结束时间：
            # 或者 当 事件的结束时间和上次事件相同的时候，我们需要判断 结束日期 与 已经安排会议的数量
            meeting_num+=1
            last_min_end_day = min_end_day
            arangmented_events.append(event)
            print(f"第 {meeting_num} 次会议，event={event}")



class EventsHeap():
    def __init__(self,events:List[List[int]]):
        self.events = events
        self.events_heap = self.build()

    def build(self,key=-1):
        """
        @author:wangfc27441
        @desc:  key =-1 默认是按照 end_day 作为key进行排序
        @version：
        @time:2021/2/24 23:01

        Parameters
        ----------

        Returns
        -------
        """
        events_heap = []
        for event in self.events:
            hq.heappush(events_heap,(event[key],event))
        return  events_heap

    def extract_min(self):
        key,event = hq.heappop(self.events_heap)
        return event

    def drop(self):
        pass

    def size(self):
        # 计算 events_heap的 数量
        return self.events_heap.__len__()

if __name__ == '__main__':
    events = [[1, 2], [2, 3], [3, 4]]
    events = [[1, 1], [1, 2], [1, 3], [1, 4], [1, 5], [1, 6], [1, 7]]
    events = [[1, 4], [4, 4], [2, 2], [3, 4], [1, 1]]

    attendMostMeeting(events=events)
    attendMostMeetingV2(events=events)



"""
605. 种花问题
假设有一个很长的花坛，一部分地块种植了花，另一部分却没有。可是，花不能种植在相邻的地块上，它们会争夺水源，两者都会死去。
给你一个整数数组  flowerbed 表示花坛，由若干 0 和 1 组成，其中 0 表示没种植花，1 表示种植了花。另有一个数 n ，能否在不打破种植规则的情况下种入 n 朵花？能则返回 true ，不能则返回 false。

示例 1：
输入：flowerbed = [1,0,0,0,1], n = 1
输出：true

示例 2：
输入：flowerbed = [1,0,0,0,1], n = 2
输出：false
 
提示：
1 <= flowerbed.length <= 2 * 104
flowerbed[i] 为 0 或 1
flowerbed 中不存在相邻的两朵花
0 <= n <= flowerbed.length
"""


"""
1578. 避免重复字母的最小删除成本
给你一个字符串 s 和一个整数数组 cost ，其中 cost[i] 是从 s 中删除字符 i 的代价。
返回使字符串任意相邻两个字母不相同的最小删除成本。
请注意，删除一个字符后，删除其他字符的成本不会改变。
 
示例 1：
输入：s = "abaac", cost = [1,2,3,4,5]
输出：3
解释：删除字母 "a" 的成本为 3，然后得到 "abac"（字符串中相邻两个字母不相同）。
示例 2：
输入：s = "abc", cost = [1,2,3]
输出：0
解释：无需删除任何字母，因为字符串中不存在相邻两个字母相同的情况。
示例 3：
输入：s = "aabaa", cost = [1,2,3,4,1]
输出：2
解释：删除第一个和最后一个字母，得到字符串 ("aba") 。
 
提示：
s.length == cost.length
1 <= s.length, cost.length <= 10^5
1 <= cost[i] <= 10^4
s 中只含有小写英文字母
"""


"""
1665. 完成所有任务的最少初始能量
给你一个任务数组 tasks ，其中 tasks[i] = [actuali, minimumi] ：
actuali 是完成第 i 个任务 需要耗费 的实际能量。
minimumi 是开始第 i 个任务前需要达到的最低能量。
比方说，如果任务为 [10, 12] 且你当前的能量为 11 ，那么你不能开始这个任务。如果你当前的能量为 13 ，你可以完成这个任务，且完成它后剩余能量为 3 。
你可以按照 任意顺序 完成任务。

请你返回完成所有任务的 最少 初始能量。

示例 1：

输入：tasks = [[1,2],[2,4],[4,8]]
输出：8
解释：
一开始有 8 能量，我们按照如下顺序完成任务：
    - 完成第 3 个任务，剩余能量为 8 - 4 = 4 。
    - 完成第 2 个任务，剩余能量为 4 - 2 = 2 。
    - 完成第 1 个任务，剩余能量为 2 - 1 = 1 。
注意到尽管我们有能量剩余，但是如果一开始只有 7 能量是不能完成所有任务的，因为我们无法开始第 3 个任务。
示例 2：

输入：tasks = [[1,3],[2,4],[10,11],[10,12],[8,9]]
输出：32
解释：
一开始有 32 能量，我们按照如下顺序完成任务：
    - 完成第 1 个任务，剩余能量为 32 - 1 = 31 。
    - 完成第 2 个任务，剩余能量为 31 - 2 = 29 。
    - 完成第 3 个任务，剩余能量为 29 - 10 = 19 。
    - 完成第 4 个任务，剩余能量为 19 - 10 = 9 。
    - 完成第 5 个任务，剩余能量为 9 - 8 = 1 。
示例 3：

输入：tasks = [[1,7],[2,8],[3,9],[4,10],[5,11],[6,12]]
输出：27
解释：
一开始有 27 能量，我们按照如下顺序完成任务：
    - 完成第 5 个任务，剩余能量为 27 - 5 = 22 。
    - 完成第 2 个任务，剩余能量为 22 - 2 = 20 。
    - 完成第 3 个任务，剩余能量为 20 - 3 = 17 。
    - 完成第 1 个任务，剩余能量为 17 - 1 = 16 。
    - 完成第 4 个任务，剩余能量为 16 - 4 = 12 。
    - 完成第 6 个任务，剩余能量为 12 - 6 = 6 。
    
"""