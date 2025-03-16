import time
from plyer import notification
from datetime import datetime

# Define the specific time you want the notification (e.g., 14:30 or 2:30 PM)
target_hour = 00  # 12 AM in 24-hour format
target_minute = 30

if __name__ == "_main_":
    while True:
        # Get the current time
        current_time = datetime.now()
        current_hour = current_time.hour
        current_minute = current_time.minute

        # Check if the current time matches the target time
        if current_hour == target_hour and current_minute == target_minute:
            notification.notify(
                title="Subject1: Mathematics",
                message="Study Module3 and Module4",
                timeout=10
            )
            # Sleep for 60 seconds to avoid multiple notifications within the same minute
            time.sleep(60)

        # Sleep for 1 second before checking again
        time.sleep(1)
targethour = 00   # 12 AM in 24-hour format
targetminute = 37

if _name_ == "_main_":
    while True:
        # Get the current time
        current_time = datetime.now()
        current_hour = current_time.hour
        current_minute = current_time.minute

        # Check if the current time matches the target time
        if current_hour == targethour and current_minute == targetminute: 
            notification.notify(
                title="Subject2: Chemistry",
                message="Study Module1 and 2 now!",
                timeout=10
            )
            # Sleep for 60 seconds to avoid multiple notifications within the same minute
            time.sleep(61)

        # Sleep for 1 second before checking again
        time.sleep(1)