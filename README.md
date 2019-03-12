Main problems with this app:
- Main Page: unable to change the backgound for the star_on and start_off buttons (by default, they are all off)
- Main Page: unable to click on the buttons star_on and star_off to change the list of favorite spots
- Main Page: can't apply the wind filter (error 400 when I request the details of each spot. Didn't find the problem)
- Details Page: error 400 when I request the details for each spot
- no tablet support on the Main Page

What worked:
- I can filter the spots based on countries
- offline support with SharedPreferences
- made sure the users can't type invalid filters
- found a way around no internet connection (Details page is not functional so I left it as it is)
- I added a button on the Details Page that opens Google Maps (only works in offline mode, when it's not making calls to the API)
- made sure there are no problems when I move from portrait mode to landscape mode