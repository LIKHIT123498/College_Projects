from pandas import DataFrame
import numpy as np
Items = []
Quantity = []
Cost1u = []
Cost = []
n = int(input('Enter the number of items: '))
for i in range(n):
    item = input('Enter the item name: ')
    Items.append(item)
    Q = int(input('Enter the quantity of the item: '))
    Quantity.append(Q)    
    cost = float(input('Enter the cost of one item: '))
    Cost1u.append(cost)
arr1 = np.array(Quantity)
arr2 = np.array(Cost1u)
Cost = (arr1 * arr2).tolist()
total = sum(Cost)
Items.append("Total")
Quantity.append(None)  
Cost1u.append(None)  
Cost.append(total)  
data = {"Items": Items, "Quantity": Quantity, "Cost(1u)": Cost1u, "Cost": Cost}
df = DataFrame(data)
print('    -----Budget_Tracker-----  ')
print(df)