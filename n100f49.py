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
start1 = datetime.datetime(2021, 3, 15, 18, 33, 19,171)
end1 = datetime.datetime(2021, 3, 15, 18, 33, 20, 221)
duration1 = (end1 - start1).seconds + (end1 - start1).microseconds * 10**(-3)

start2 = datetime.datetime(2021, 3, 15, 18, 53, 25, 226)
end2 = datetime.datetime(2021, 3, 15, 18, 53, 26, 266)
duration2 = (end2 - start2).seconds + (end2 - start2).microseconds * 10**(-3)

start3 = datetime.datetime(2021, 3, 15, 18, 55, 6, 813)
end3 = datetime.datetime(2021, 3, 15, 18, 55, 7, 856)
duration3 = (end3 - start3).seconds + (end3 - start3).microseconds * 10**(-3)

start4 = datetime.datetime(2021, 3, 15, 18, 56, 8, 346)
end4 = datetime.datetime(2021, 3, 15, 18, 56, 9, 382)
duration4 = (end4 - start4).seconds + (end4 - start4).microseconds * 10**(-3)

start5 = datetime.datetime(2021, 3, 15, 18, 58, 13, 360)
end5 = datetime.datetime(2021, 3, 15, 18, 58, 14, 398)
duration5 = (end5 - start5).seconds + (end5 - start5).microseconds * 10**(-3)

print([duration1, duration2, duration3, duration4, duration5])
durationList500 = [duration1, duration2, duration3, duration4, duration5]

# N = 10, f = 4, timeout = 1000
start1 = datetime.datetime(2021, 3, 15, 19, 4, 30, 213)
end1 = datetime.datetime(2021, 3, 15, 19, 4, 30, 237)
duration1 = (end1 - start1).seconds + (end1 - start1).microseconds * 10**(-3)

start2 = datetime.datetime(2021, 3, 15, 19, 6, 36, 147)
end2 = datetime.datetime(2021, 3, 15, 19, 6, 36, 179)
duration2 = (end2 - start2).seconds + (end2 - start2).microseconds * 10**(-3)

start3 = datetime.datetime(2021, 3, 15, 19, 7, 55, 503)
end3 = datetime.datetime(2021, 3, 15, 19, 7, 55, 525)
duration3 = (end3 - start3).seconds + (end3 - start3).microseconds * 10**(-3)

start4 = datetime.datetime(2021, 3, 15, 19, 10, 25, 189)
end4 = datetime.datetime(2021, 3, 15, 19, 10, 25, 221)
duration4 = (end4 - start4).seconds + (end4 - start4).microseconds * 10**(-3)

start5 = datetime.datetime(2021, 3, 15, 19, 11, 31, 278)
end5 = datetime.datetime(2021, 3, 15, 19, 11, 31, 304)
duration5 = (end5 - start5).seconds + (end5 - start5).microseconds * 10**(-3)

print([duration1, duration2, duration3, duration4, duration5])
durationList1000 = [duration1, duration2, duration3, duration4, duration5]


# N = 10, f = 4, timeout = 1500  
start1 = datetime.datetime(2021, 3, 15, 20, 9, 11, 501)
end1 = datetime.datetime(2021, 3, 15, 20, 9, 11, 527)
duration1 = (end1 - start1).seconds + (end1 - start1).microseconds * 10**(-3)

start2 = datetime.datetime(2021, 3, 15, 20, 9, 20, 63)
end2 = datetime.datetime(2021, 3, 15, 20, 9, 20, 92)
duration2 = (end2 - start2).seconds + (end2 - start2).microseconds * 10**(-3)

start3 = datetime.datetime(2021, 3, 15, 20, 9, 25, 851)
end3 = datetime.datetime(2021, 3, 15, 20, 9, 25, 868)
duration3 = (end3 - start3).seconds + (end3 - start3).microseconds * 10**(-3)

start4 = datetime.datetime(2021, 3, 15, 20, 9, 36, 943)
end4 = datetime.datetime(2021, 3, 15, 20, 9, 36, 965)
duration4 = (end4 - start4).seconds + (end4 - start4).microseconds * 10**(-3)

start5 = datetime.datetime(2021, 3, 15, 20, 9, 7, 908)
end5 = datetime.datetime(2021, 3, 15, 20, 9, 7, 940)
duration5 = (end5 - start5).seconds + (end5 - start5).microseconds * 10**(-3)

print([duration1, duration2, duration3, duration4, duration5])
durationList1500 = [duration1, duration2, duration3, duration4, duration5]

# N = 10, f = 4, timeout = 2000  
start1 = datetime.datetime(2021, 3, 15, 20, 16, 47, 225)
end1 = datetime.datetime(2021, 3, 15, 20, 16, 48, 252)
duration1 = (end1 - start1).seconds + (end1 - start1).microseconds * 10**(-3)

start2 = datetime.datetime(2021, 3, 15, 20, 16, 39, 178)
end2 = datetime.datetime(2021, 3, 15, 20, 16, 40, 216)
duration2 = (end2 - start2).seconds + (end2 - start2).microseconds * 10**(-3)

start3 = datetime.datetime(2021, 3, 15, 20, 16, 11, 529)
end3 = datetime.datetime(2021, 3, 15, 20, 16, 12, 558)
duration3 = (end3 - start3).seconds + (end3 - start3).microseconds * 10**(-3)

start4 = datetime.datetime(2021, 3, 15, 20, 16, 37, 527)
end4 = datetime.datetime(2021, 3, 15, 20, 16, 38, 555)
duration4 = (end4 - start4).seconds + (end4 - start4).microseconds * 10**(-3)

start5 = datetime.datetime(2021, 3, 15, 20, 16, 24, 148)
end5 = datetime.datetime(2021, 3, 15, 20, 16, 25, 164)
duration5 = (end5 - start5).seconds + (end5 - start5).microseconds * 10**(-3)
print([duration1, duration2, duration3, duration4, duration5])
durationList2000 = [duration1, duration2, duration3, duration4, duration5]

PlotEvolution(durationList500, durationList1000, durationList1500, durationList2000)

fig = plt.figure()
ax = plt.axes()
ax.plot([0.5, 1, 1.5, 2], [Average(durationList500), Average(durationList1000), Average(durationList1500), Average(durationList2000)]);
plt.title("Average duration to reach consensus according to Tle")
plt.xlabel("Tle")
plt.ylabel("Duration (ms)")