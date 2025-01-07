# customer_rewards
Problem Description: 
A retailer offers a rewards program to its customers, awarding points based on each recorded purchase.
A customer receives 2 points for every dollar spent over $100 in each transaction, plus 1 point for every dollar spent between $50 and $100 in each transaction. (e.g. a $120 purchase = 2x$20 + 1x$50 = 90 points).

Endpoint URL: /api/rewards/getWebRewardPoints

Sample I/P: 
"customerTransactions" : [
{
"customerId":1,
"customerName": "John Doe",
"month": "2023-10",
"amount":120.0
},
{
"customerId":2,
"customerName": "Jane Smith",
"month": "2023-10",
"amount":75.0
},
{
"customerId":3,
"customerName": "John Doe",
"month": "2023-11",
"amount":200.0
},
{
"customerId":4,
"customerName": "Jane Smith",
"month": "2023-11",
"amount":50.0
},
{
"customerId":5,
"customerName": "John Doeee",
"month": "2025-01",
"amount":130.0
},
{
"customerId":6,
"customerName": "Jane Smith",
"month": "2024-12",
"amount":90.0
},
{
"customerId":5,
"customerName": "John Doeee",
"month": "2024-12",
"amount":140.0
}
],
"months" : 2

Sample O/P:
[
  {
    "customerId": 5,
    "customerTransactions": [
      {
        "customerId": 5,
        "customerName": "John Doeee",
        "month": "2025-01",
        "amount": 130
      },
      {
        "customerId": 5,
        "customerName": "John Doeee",
        "month": "2024-12",
        "amount": 140
      }
    ],
    "monthlyPoints": {
      "2025-01": 110,
      "2024-12": 130
    },
    "totalPoints": 240
  },
  {
    "customerId": 6,
    "customerTransactions": [
      {
        "customerId": 6,
        "customerName": "Jane Smith",
        "month": "2024-12",
        "amount": 90
      }
    ],
    "monthlyPoints": {
      "2024-12": 40
    },
    "totalPoints": 40
  }
]
