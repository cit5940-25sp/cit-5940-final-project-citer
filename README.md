
  $$$$$     $$     $$   $$$$$$$$$   $$$$$$$$$   $$$$$$$$$$$    $$$$$$$$$$$$     $$$$$$$$$$$$$$
$$     $$   $$     $$   $$     $$       $$      $$       $$   $$          $$          $$
$$          $$     $$   $$     $$       $$      $$      $$   $$            $$         $$
$$          $$$$$$$$$   $$$$$$$$$       $$      $$ $$ $$    $$              $$        $$
$$          $$     $$   $$     $$       $$      $$      $$   $$            $$         $$
$$     $$   $$     $$   $$     $$       $$      $$       $$   $$          $$          $$
  $$$$$     $$     $$   $$     $$       $$      $$$$$$$$$$     $$$$$$$$$$             $$


  
Core Features and Data Structures:

The chatbot is a fixed rule based prompt - the core rule -> response feature is built 
with the help of a HashMap <Rule, Response>. With each rule pointing to the specific
functional based response.

    Lifestyle Planning (Restaurant recommendations/ Things to do in Philly.):
        a. The bot will also recommend things to do around campus or food to eat
        based on the user’s moods 🙂
        b.  We have planned on using a Mood-Food Graph(HashMap<Mood,
        Priority Queues> with ratings as weights)/ Heaps/ Trees which a user
        can explore the places to eat around the campus based on their mood !
        The moods can be linked to specific cuisines (fast food/ gourmet etc.) and
        we can use a graph traversal strategy to find spots based on the moods.

Features:

I first analyse the mood of the user first
Then Start with analysing the requirements from the user
Then start analysing the restaurants - 

Make a mood categorisation - understand which mood requires what food

My strategy - First ensure that the mood remains stable - if it is towards
the upper end - keep it there

If it is to the lower end of the spectrum start making sure that the mood 
returns back to normal

Often food is not the sole key factor that will balance the mood

Here the next important thing is to be able to assess what the user is going through

To combat this - an optional feature that I can integrate with lifestyle is something 
related to mental health ! So the user's prompts can be analysed to assess whether
the user - NEEDS to do certain activities

Just like a therapist 

Only thing here is that this bot will ask the user ... should I add the activity on your to do list for the day ?

Eg: You look overwhelmed -> do you want me to add walk to the XYZ park to the day's to-do list ?




