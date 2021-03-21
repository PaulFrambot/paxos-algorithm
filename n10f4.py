import datetime
import matplotlib.pyplot as plt


def PlotEvolution(durationList500, durationList1000, durationList1500, durationList2000) :
    fig = plt.figure()
    ax = plt.axes()
    ax.plot([1, 2, 3, 4, 5], durationList500);
    ax.plot([1, 2, 3, 4, 5], durationList1000);
    ax.plot([1, 2, 3, 4, 5], durationList1500);
    ax.plot([1, 2, 3, 4, 5], durationList2000);
    plt.title("Time to reach consensus in different tests")
    plt.legend(['Tle = 500', 'Tle = 1000', 'Tle = 1500', 'Tle = 2000'])
    plt.xlabel("Test number")
    plt.ylabel("Time to reach consensus")

def Average(lst): 
    return sum(lst) / len(lst) 



# N = 10, f = 4, timeout = 500
start1 = datetime.datetime(2021, 3, 15, 18, 33, 19,635)
end1 = datetime.datetime(2021, 3, 15, 18, 33, 19, 649)
duration1 = (end1 - start1).microseconds

start2 = datetime.datetime(2021, 3, 15, 18, 53, 47, 130)
end2 = datetime.datetime(2021, 3, 15, 18, 53, 47, 147)
duration2 = (end2 - start2).microseconds

start3 = datetime.datetime(2021, 3, 15, 18, 55, 23, 504)
end3 = datetime.datetime(2021, 3, 15, 18, 55, 23, 524)
duration3 = (end3 - start3).microseconds

start4 = datetime.datetime(2021, 3, 15, 18, 56, 44, 236)
end4 = datetime.datetime(2021, 3, 15, 18, 56, 44, 253)
duration4 = (end4 - start4).microseconds

start5 = datetime.datetime(2021, 3, 15, 18, 58, 19, 724)
end5 = datetime.datetime(2021, 3, 15, 18, 58, 19, 741)
duration5 = (end5 - start5).microseconds
print([duration1, duration2, duration3, duration4, duration5])
durationList500 = [duration1, duration2, duration3, duration4, duration5]

# N = 10, f = 4, timeout = 1000
start1 = datetime.datetime(2021, 3, 15, 19, 4, 36, 683)
end1 = datetime.datetime(2021, 3, 15, 19, 4, 36, 697)
duration1 = (end1 - start1).microseconds

start2 = datetime.datetime(2021, 3, 15, 19, 6, 13, 474)
end2 = datetime.datetime(2021, 3, 15, 19, 6, 13, 492)
duration2 = (end2 - start2).microseconds

start3 = datetime.datetime(2021, 3, 15, 19, 7, 43, 337)
end3 = datetime.datetime(2021, 3, 15, 19, 7, 43, 349)
duration3 = (end3 - start3).microseconds

start4 = datetime.datetime(2021, 3, 15, 19, 10, 25, 619)
end4 = datetime.datetime(2021, 3, 15, 19, 10, 25, 632)
duration4 = (end4 - start4).microseconds

start5 = datetime.datetime(2021, 3, 15, 19, 11, 31, 268)
end5 = datetime.datetime(2021, 3, 15, 19, 11, 31, 290)
duration5 = (end5 - start5).microseconds

print([duration1, duration2, duration3, duration4, duration5])
durationList1000 = [duration1, duration2, duration3, duration4, duration5]

# N = 10, f = 4, timeout = 1500  
start1 = datetime.datetime(2021, 3, 15, 20, 9, 22, 829)
end1 = datetime.datetime(2021, 3, 15, 20, 9, 22, 847)
duration1 = (end1 - start1).microseconds

start2 = datetime.datetime(2021, 3, 15, 20, 9, 22, 364)
end2 = datetime.datetime(2021, 3, 15, 20, 9, 22, 385)
duration2 = (end2 - start2).microseconds

start3 = datetime.datetime(2021, 3, 15, 20, 9, 22, 218)
end3 = datetime.datetime(2021, 3, 15, 20, 9, 22, 229)
duration3 = (end3 - start3).microseconds

start4 = datetime.datetime(2021, 3, 15, 20, 9, 22, 590)
end4 = datetime.datetime(2021, 3, 15, 20, 9, 22, 609)
duration4 = (end4 - start4).microseconds

start5 = datetime.datetime(2021, 3, 15, 20, 9, 22, 267)
end5 = datetime.datetime(2021, 3, 15, 20, 9, 22, 287)
duration5 = (end5 - start5).microseconds

print([duration1, duration2, duration3, duration4, duration5])
durationList1500 = [duration1, duration2, duration3, duration4, duration5]

# N = 10, f = 4, timeout = 2000  
start1 = datetime.datetime(2021, 3, 15, 20, 16, 3, 883)
end1 = datetime.datetime(2021, 3, 15, 20, 16, 3, 898)
duration1 = (end1 - start1).microseconds

start2 = datetime.datetime(2021, 3, 15, 20, 16, 3, 684)
end2 = datetime.datetime(2021, 3, 15, 20, 16, 3, 701)
duration2 = (end2 - start2).microseconds

start3 = datetime.datetime(2021, 3, 15, 20, 16, 3, 795)
end3 = datetime.datetime(2021, 3, 15, 20, 16, 3, 811)
duration3 = (end3 - start3).microseconds

start4 = datetime.datetime(2021, 3, 15, 20, 16, 3, 254)
end4 = datetime.datetime(2021, 3, 15, 20, 16, 3, 277)
duration4 = (end4 - start4).microseconds

start5 = datetime.datetime(2021, 3, 15, 20, 16, 3, 441)
end5 = datetime.datetime(2021, 3, 15, 20, 16, 3, 451)
duration5 = (end5 - start5).microseconds
print([duration1, duration2, duration3, duration4, duration5])
durationList2000 = [duration1, duration2, duration3, duration4, duration5]

PlotEvolution(durationList500, durationList1000, durationList1500, durationList2000)

fig = plt.figure()
ax = plt.axes()
ax.plot([0.5, 1, 1.5, 2], [Average(durationList500), Average(durationList1000), Average(durationList1500), Average(durationList2000)]);
plt.title("Average duration to reach consensus according to Tle")
plt.xlabel("Tle")
plt.ylabel("Duration (ms)")