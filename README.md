# SC2002 Project
Working directory for Restaurant Rush project
<br>
To run the game, use the command `java RestaurantUI.java` while in the sc2002project folder

## stage-1 -> stage-2 changes 
New files committed at [b8bdeb3](https://github.com/Ash3926/SC2002_Project/commit/b8bdeb3447a7e67669c78e348db1e408ad62483c) and [6bbe8ba](https://github.com/Ash3926/SC2002_Project/commit/6bbe8ba15a07dd82aa00241f385303f8b56da5d9)<br>
Existing file changes committed at [7c65194](https://github.com/Ash3926/SC2002_Project/commit/7c6519449d4faa2f4261cb1390f7658d00ac3a54)

**New Files:**
- Customer/Critic.java
- Customer/VIP.java

**Existing Files Changed:**
- Customer/Customer.java
    - Changed satisfaction to protected instead of private so the child classes could override the applyWaitingPenalty method
    - Added protected variable billing, which represents the percentage of bill the customer must pay
- Staff/Cashier.java
    - Updated handlePayment method to account for VIP discount
- RestaurantGame.java
    - Updated customerArrival method to introduce the Critic and VIP customers on Turn 4 and 10 respectively
