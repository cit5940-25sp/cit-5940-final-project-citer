[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/nK589Lr0)
<<<<<<< HEAD
[![Open in Visual Studio Code](https://classroom.github.com/assets/open-in-vscode-2e0aaae1b6195c2367325f4f02e2d04e9abb55f0b24a779b69b11b9e10269abc.svg)](https://classroom.github.com/online_ide?assignment_repo_id=18841701&assignment_repo_type=AssignmentRepo)
=======
[![Open in Visual Studio Code](https://classroom.github.com/assets/open-in-vscode-2e0aaae1b6195c2367325f4f02e2d04e9abb55f0b24a779b69b11b9e10269abc.svg)](https://classroom.github.com/online_ide?assignment_repo_id=18841718&assignment_repo_type=AssignmentRepo)
>>>>>>> 4a6eb56 (add online IDE url; add deadline)
# Final Project Template

This is an example of a project directory for you to start working from. Please use it!


```text
|
|--- .gitignore # lists all of the junk that might exist in your folder that should not be committed
|--- README.md 
    We built a rule-based fixed prompt Chatbot, specifically geared towards students at UPenn. 
    This Chat bot will serve as a personal assistant to the students.
    As international students, it took us considerable time to get adapted to life @ penn and understand the program. 
    We all were of the opinion that providing resources to the future students in the form of a ChatBot 
    will definitely be useful and help them adapt into Philly, Penn and the MCIT program more quickly.
    
    To run the Chat Bot please navigate to the ChatBot class and run the main in that class.
    
    The current functionality provides help with:
        1. Course selection
        2. To do list
        3. Food and Restaurant recommendations
        4. Course review
        5. Travel location recommendations
    
    
|--- src
    |----- AcademicCommand.java  
    |----- AcademicPlannerUI.java         
    |----- ChatBot.java       
    |----- Colors.java           
    |----- Command.java         
    |----- Course.java
    |----- CourseGraph.java
    |----- CourseLoader.java
    |----- CoursePlanner.java
    |----- CourseRecommendation.java
    |----- CourseReview.java         
    |----- CourseReviewData.java 
    |----- CuisineStrategy.java 
    |----- DailyPlanner.java 
    |----- DestinationNode.java     
    |----- FoodCommand.java  
    |----- FoodGraph.java    
    |----- FoodRecommendationStrategy.java 
    |----- GoodMoodStrategy.java 
    |----- InterestAreaManager.java 
    |----- Node.java  
    |----- NotGoodMoodStrategy.java 
    |----- RandomStrategy.java   
    |----- RandomTravelStrategy.java  
    |----- ReviewCommand.java   
    |----- Task.java             
    |----- TodoCommand.java  
    |----- TravelCommand.java        
    |----- TravelData.java  
    |----- TravelFoodUI.java 
    |----- TravelStrategyFactory.java 
    |----- TravelTagRecommendation.java 
    |----- Trie.java
    |----- TrieNode.java     
    |----- UserTravelStrategy.java  
|--- test
    |----- AcademicPlanningTest.java
    |----- ChatBotTest.java
    |----- CourseGraphTest.java
    |----- CourseLoaderTest.java
    |----- CoursePlannerTest.java
    |----- CourseRecommendationTest.java
    |----- CourseReviewDataTest.java
    |----- CourseReviewTest.java
    |----- CourseTest.java
    |----- DailyPlannerTest.java
    |----- FoodCommandTest.java
    |----- FoodGraphTest.java
    |----- InterestAreaManagerTest.java
    |----- RestaurantDataTest.java
    |----- TaskTest.java
    |----- TestDestinationNode.java
    |----- TestNodeFood.java
    |----- TodoCommandTest.java
    |----- TravelCommandTest.java
    |----- TravelDataTest.java
    |----- TravelStrategyTest.java
    |----- TrieNodeTest.java
    |----- TrieTest.java
|--- data
    |----- prereq.csv
    |----- cis_courses.csv
    |----- food_dataset.csv
    |----- Holidays_calendar.csv
    |----- place_and_category.csv
    |----- destination_details.csv
    
```

Design pattern: 1) Command Design pattern & 2) Strategy Design pattern

[📄 View UML Diagram (CITerFinal.drawio.pdf)](CITerFinal.drawio.pdf)



Resources used for building the data sets:

1. Food Database: (food_dataset.csv)
    Primary Source: https://docs.google.com/spreadsheets/d/1CMosrdfNzjkUW9Svkm0dPuIjv4a2jcOg/edit?gid=1143306179#gid=1143306179
    Yelp/ Google reviews: Used the web scraping and lookup feature of ChatGPT to scrape the reviews for all the restaurants in the data set.

2. Holiday Calendar: (Holidays_calendar.csv):
    Primary Source: https://almanac.upenn.edu/penn-academic-calendar

3. Category and Places: (place_and_category.csv, destination_details.csv):
   Primary Source: Used the research and web scraping feature of ChatGPT to curate a list of holiday destinations tagged to a category
                    and for each destination - got the top things to see, activities to do and must try food along with costs per day.