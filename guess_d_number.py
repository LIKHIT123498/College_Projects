import random
import sys
for i in range(1,11):
 num=int(input('Guess a number between 1 to 10:'))
 n=random.randint(0,10)
 if n==num:
    print('You have guessed the right number!')
    sys.exit()
 elif i<10:
   print(f'{10-i} attempt(s) left')
   print('Try Again!')
 else:
  print('Game over')