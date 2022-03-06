def sum_recursion(arr):
    print(arr)
    if len(arr) == 1:
        print("BACK")
        return arr[0]
    else:
        print("DIVE IN")
        return arr.pop(0) + sum_recursion(arr)

print(sum_recursion([4, 5, 6, 5, 10, -1]))

