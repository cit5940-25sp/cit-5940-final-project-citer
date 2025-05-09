[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/nK589Lr0)
[![Open in Visual Studio Code](https://classroom.github.com/assets/open-in-vscode-2e0aaae1b6195c2367325f4f02e2d04e9abb55f0b24a779b69b11b9e10269abc.svg)](https://classroom.github.com/online_ide?assignment_repo_id=18841701&assignment_repo_type=AssignmentRepo)
# Final Project Template

This is an example of a project directory for you to start working from. Please use it!


```text
|
|--- .gitignore # lists all of the junk that might exist in your folder that should not be committed
|--- README.md # explanation for the purpose of your repo
|--- src
    |----- *.java (source code files)
    ├── ChatBot.java          (the brain)
    ├── Command.java          (the command interface)
    ├── AcademicCommand.java  (course planning feature)
    ├── TodoCommand.java      (daily planner feature)
    ├── FoodCommand.java      (food recommendations feature)
    ├── CoursePlanner.java    (DAG for course recommendations)
    ├── DailyPlanner.java     (Stack for tasks)
    ├── Task.java              (Represents a to-do task)
    ├── Restaurant.java        (Represents a restaurant)
    ├── RestaurantData.java    (Load restaurants from CSV)
    ├── FoodRecommendationStrategy.java (Strategy pattern interface)
    ├── HighestRatedStrategy.java (Concrete strategy)
    ├── RandomStrategy.java     (Concrete strategy)
    ├── CourseReviewLoader.java (Loader if you want course reviews)
    ├── CourseReview.java       (Course review object)
    ├── ReviewCommand.java      (Optional: View course reviews)
|--- test
    |----- *Test.java (unit test files)
├── data
    ├── restaurant_data.csv
    ├── cis_course_reviews.csv
```

design pattern: command & startegy


Food data set: https://docs.google.com/spreadsheets/d/1CMosrdfNzjkUW9Svkm0dPuIjv4a2jcOg/edit?gid=1143306179#gid=1143306179

