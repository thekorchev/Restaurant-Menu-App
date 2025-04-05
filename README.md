# Restaurant Menu App

This is the last 5/5 stage for app createion from this project https://hyperskill.org/projects/335

My account in Hyperskill: https://hyperskill.org/my-learning/495086218

## Make the order Stage 5/5
## Description
Waiters can input the amount ordered for every item on menu, but they still can't register the order.

Let's add a button to register the order. When an order is made send a visual feedback with the order summary. Also the amount stock should be decreased, the order amount for each item should be reset to zero and the limit on new orders should follow the new stock state.
## Objectives
1. Add a Button with background color black and content color white. Inside the Button add a Text with text "Make Order" and font size 24sp
2. Add an onClick listener on the button that trigger an event to register the order
 - If the button is clicked with all order quantities equal to zero then do nothing
 - If the button is clicked and one item only has quantity greater than zero then Toast a message with the following format "Ordered:\n==> ${recipeName}: $orderAmount" and update the stock amount of that recipe
 - If the button is clicked and many items have quantity greater than zero then Toast a message that besides the same header as the case above also has one line for each of those items using the same recipe line format as the case above and update the stock of each ordered item
3. After an order is made
 - Update the stock amount
 - Visual cues and limit to order amount should follow the new state of stock amount
 - Prepare to receive new orders by resetting ordered amount to 0
## Example
![Process](https://ucarecdn.com/e4f2a9e4-c03a-4256-a631-b3533ea3386b/)
