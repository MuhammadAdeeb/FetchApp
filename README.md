Requirements: 
        - Retrieve data from https://fetch-hiring.s3.amazonaws.com/hiring.json.
        - Display all the items grouped by "listId"
        - Sort the results first by "listId" then by "name" when displaying.
        - Filter out any items where "name" is blank or null.

# FetchApp
Using multithreading, the app makes an API call to pull JSON data from above link. 
    Sidenotes: 
        1) Volley Library is used to make the API call
        2) JSON data format: [ {"id": 755, "listId": 2, "name": ""}, ... ]

Second Thread:
    The JSON is parsed and stored into a Hashmap where key is the listId and value is an Integer Arraylist of ids where name isn't an empty 
    string nor null (I originally thought of storing name string in the arraylist and sorting it using an (int) substring within name string
    (to display according to the order requirement); however, b/c id is the same integer as the integer in name, I chose to use id. 

    Then, I sorted the Hashmap by key values (listIds); another order requirement.

    Then, I create an arraylist of FetchItems object using the Hashmap, which is returned to the MainUIThread

    Sidenote:
        1) FetchItem class stores key of hashmap as 'listId' element, value (an arraylist) in 'arr_id' element, creates a 'fin_str'
        element (a final string to go in the textview for user to see on UI) and 'str_names' Arraylist in case we want access to names.

Back to Main Thread: 
    FetchItems Arraylist received is passed to an adapter used to display data in a recyclerView for good design and display for the user.
    

Additional Thoughts:
- If the data retrieved contained user specific or company specific information, I could have the user interact more with it (Clicking on 
it to lead to YouTube, Google Maps, Mail etc.)
- I did ensure Network Connection check, so nothing would make the App crash/break. If it does, please let me know
