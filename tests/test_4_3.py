def find_max_in_list(current, arr):
    if len(arr) == 0:
        print("HARD STOP")
        return -1
    elif len(arr) == 1:
        print("BACK")
        return arr[0]
    else:
        tmp = arr.pop(0)
        print("TMP[0]=", tmp)
        if current < tmp:
            current = tmp
        tmp = find_max_in_list(current, arr)
        print("TMP[f_m_i_l]=", tmp)
        if current < tmp:
            current = tmp
        return current

def max(list):
    if len(list) == 2:
        return list[0] if list[0] > list[1] else list[1]
    sub_max = max(list[1:])
    return list[0] if list[0] > sub_max else sub_max

#print(find_max_in_list(0, [1, 4, 5, 100, 2, 1]))
print(max([4, 5, 100, 1, 2, 4, 100, 99]))