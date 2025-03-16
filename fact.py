def fact(n):
    total=1
    for i in range (1,n+1):
     total=total*(i)
    return total
n=int(input('Enter the value of n:'))
r=int(input('Enter the value of r:'))
d=n-r
nCr=fact(n)/(fact(r)*fact(d))
print('nCr:',nCr)
				
