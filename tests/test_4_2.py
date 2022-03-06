def count_recursion(arr):
    if len(arr) == 1:
        print("BACK")
        return 1
    else:
        arr.pop()
        print("DIVE IN")
        return 1 + count_recursion(arr)

print(count_recursion([1, 2, 3, 4, 5, 6, 7, 8, 9, 10]))