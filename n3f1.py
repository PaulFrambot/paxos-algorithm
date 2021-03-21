import datetime
import matplotlib.pyplot as plt


def PlotEvolution(durationList500, durationList1000, durationList1500, durationList2000) :
    fig = plt.figure()
    ax = plt.axes()
    ax.plot([1, 2, 3, 4, 5], durationList500);
    ax.plot([1, 2, 3, 4, 5], durationList1000);
    ax.plot([1, 2, 3, 4, 5], durationList1500);
    ax.plot([1, 2, 3, 4, 5], durationList2000);
    plt.title("tle")
    plt.legend(['Tle = 500', 'Tle = 1000', 'Tle = 1500', 'Tle = 2000'])
    plt.xlabel("itération")
    plt.ylabel("duration in ms")
    
def Average(lst): 
    return sum(lst) / len(lst) 


# N = 3, f = 1, timeout = 500
start1 = datetime.datetime(2021, 3, 15, 18, 33, 19,645)
end1 = datetime.datetime(2021, 3, 15, 18, 33, 19, 653)
duration1 = (end1 - start1).microseconds

start2 = datetime.datetime(2021, 3, 15, 18, 53, 47, 697)
end2 = datetime.datetime(2021, 3, 15, 18, 53, 47, 704)
duration2 = (end2 - start2).microseconds

start3 = datetime.datetime(2021, 3, 15, 18, 55, 23, 237)
end3 = datetime.datetime(2021, 3, 15, 18, 55, 23, 244)
duration3 = (end3 - start3).microseconds

start4 = datetime.datetime(2021, 3, 15, 18, 56, 44, 565)
end4 = datetime.datetime(2021, 3, 15, 18, 56, 44, 574)
duration4 = (end4 - start4).microseconds

start5 = datetime.datetime(2021, 3, 15, 18, 58, 19, 796)
end5 = datetime.datetime(2021, 3, 15, 18, 58, 19, 802)
duration5 = (end5 - start5).microseconds
print([duration1, duration2, duration3, duration4, duration5])
durationList500 = [duration1, duration2, duration3, duration4, duration5]


# N = 3, f = 1, timeout = 1000
start1 = datetime.datetime(2021, 3, 15, 19, 4, 36, 259)
end1 = datetime.datetime(2021, 3, 15, 19, 4, 36, 264)
duration1 = (end1 - start1).microseconds

start2 = datetime.datetime(2021, 3, 15, 19, 6, 13, 573)
end2 = datetime.datetime(2021, 3, 15, 19, 6, 13, 584)
duration2 = (end2 - start2).microseconds

start3 = datetime.datetime(2021, 3, 15, 19, 7, 43, 938)
end3 = datetime.datetime(2021, 3, 15, 19, 7, 43, 943)
duration3 = (end3 - start3).microseconds

start4 = datetime.datetime(2021, 3, 15, 19, 10, 25, 894)
end4 = datetime.datetime(2021, 3, 15, 19, 10, 25, 902)
duration4 = (end4 - start4).microseconds

start5 = datetime.datetime(2021, 3, 15, 19, 11, 31,941)
end5 = datetime.datetime(2021, 3, 15, 19, 11, 31, 946)
duration5 = (end5 - start5).microseconds

print([duration1, duration2, duration3, duration4, duration5])
durationList1000 = [duration1, duration2, duration3, duration4, duration5]

# N = 3, f = 1, timeout = 1500  
start1 = datetime.datetime(2021, 3, 15, 20, 9, 22, 886)
end1 = datetime.datetime(2021, 3, 15, 20, 9, 22, 897)
duration1 = (end1 - start1).microseconds

start2 = datetime.datetime(2021, 3, 15, 20, 9, 22,370)
end2 = datetime.datetime(2021, 3, 15, 20, 9, 22,382)
duration2 = (end2 - start2).microseconds

start3 = datetime.datetime(2021, 3, 15, 20, 9, 22,368)
end3 = datetime.datetime(2021, 3, 15, 20, 9, 22, 376)
duration3 = (end3 - start3).microseconds

start4 = datetime.datetime(2021, 3, 15, 20, 9, 22, 17)
end4 = datetime.datetime(2021, 3, 15, 20, 9, 22, 27)
duration4 = (end4 - start4).microseconds

start5 = datetime.datetime(2021, 3, 15, 20, 9, 22, 851)
end5 = datetime.datetime(2021, 3, 15, 20, 9, 22, 864)
duration5 = (end5 - start5).microseconds

print([duration1, duration2, duration3, duration4, duration5])
durationList1500 = [duration1, duration2, duration3, duration4, duration5]


# N = 3, f = 1, timeout = 2000  
start1 = datetime.datetime(2021, 3, 15, 20, 16, 3, 811)
end1 = datetime.datetime(2021, 3, 15, 20, 16, 3, 816)
duration1 = (end1 - start1).microseconds

start2 = datetime.datetime(2021, 3, 15, 20, 16, 3, 445)
end2 = datetime.datetime(2021, 3, 15, 20, 16, 3, 454)
duration2 = (end2 - start2).microseconds

start3 = datetime.datetime(2021, 3, 15, 20, 16, 3, 352)
end3 = datetime.datetime(2021, 3, 15, 20, 16, 3, 363)
duration3 = (end3 - start3).microseconds

start4 = datetime.datetime(2021, 3, 15, 20, 16, 3, 428)
end4 = datetime.datetime(2021, 3, 15, 20, 16, 3, 444)
duration4 = (end4 - start4).microseconds

start5 = datetime.datetime(2021, 3, 15, 20, 16, 3, 234)
end5 = datetime.datetime(2021, 3, 15, 20, 16, 3, 242)
duration5 = (end5 - start5).microseconds
print([duration1, duration2, duration3, duration4, duration5])
durationList2000 = [duration1, duration2, duration3, duration4, duration5]
PlotEvolution(durationList500, durationList1000, durationList1500, durationList2000)


fig = plt.figure()
ax = plt.axes()
ax.plot([0.5, 1, 1.5, 2], [Average(durationList500), Average(durationList1000), Average(durationList1500), Average(durationList2000)]);
plt.title("Mean duration according to Tle")
plt.xlabel("Tle")
plt.ylabel("Mean duration in ms")