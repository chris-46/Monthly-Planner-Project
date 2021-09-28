# ***The Monthly Planner Project: A Time Management Intiative***
##### *Author: Christopher Brian Tjondro*
A project inspired by the struggles of a student to find time amidst all the schoolwork.

## **Inspiration and Description:**
As a university student in the third year taking 5 courses a term, a lot of my time has to be spent working on assignments.
In fact, I'm often left tired at the end of the day with no time left to do the things I love or to socialize with my friends.
Despite this, I have always maintained the belief that I will find enough time to do all the things I love if I manage it properly.

In accordance with that, I created this project to inspire and enable myself and fellow students to better manage our time and make time for what we want. 
While simple, a month planner is perfect for University students who live a week (or even a day) at a time. Specifications for assignments and chapter lists for midterms are often 
released less than a month in advance, and so a month is usually the sweet spot for preparing early. 

With this project, I hope to build and maintain a sense of proper time management in myself and fellow students in order to allow for proper balance between school and play.
## The Monthly Planner's main objectives are to:
- **Centralize** deadlines and events for the month.
- Provide **scheduling functionality** (adding and removing events).

## **User Stories:**
- As a user, I want to be able to *add* an **event** to my monthly planner.
- As a user, I want to be able to *remove* an **event** from my monthly planner.
- As a user, I want to be able to *edit* an **event's description** in my monthly planner.
- As a user, I want to be able to *mark/unmark* an event as **completed**. 
- As a user, I want to be able to *list* **all events** in my monthly planner.       
- As a user, I want to be able to *save* my **Planner** to file
- As a user, I want to be able to be able to *load* my **Planner** from file                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        

Phase 4: Task 2
I chose to test and design a class in my Day class in the removeEventItem() method.
The Exception itself is caught in the PlannerApp class in the removeEvent() method.
The test can be found in the PlannerTest class. The test is testRemoveEventItemEmpty().

Phase 4: Task 3
I like the overall design of my Planner. It is simple, highly cohesive, and has low coupling.
Each class does its function well (like Day manages events within it, Planner app stringing classes together for running, etc).
There is a lack of abstract classes and interfaces outside of Writable as the simple nature of this project simply does not require it.
There is only one general EventItem variant, of which a list of them is stored in a Day object, and a list of said Days are kept in a Planner.
That being said, having a clear structure with only one type of each object (no EventItemA and EventItemB variants, for example) and one function per class makes the project relatively simple.
Several duplicate methods do exist, as there are variants of methods like addEvent(). While it could be streamlined to reduce duplicate code, 
it is intentionally split to make maintaining code easier (and to lower coupling). 
One is used for the console version of the program, while the other is used for the GUI version.
In the console version, PlannerApp interacts directly with the user through a scanner. As a result, the addEvent method it has takes in user input one by one before invoking the method to add a new event with the obtained parameters.
The GUI version does this differently, utilizing textboxes and JOptionPanes to obtain data. Since they do this in different ways, it makes sense to separate them rather than create a hybrid method that tries to serve both functions simultaneously.
If such a method were to exist, it would be longer than it needs to be, it would perform more than one logical task, and one would need to update both console AND GUI versions of the Planner to deal with changes in the method.
As such, it is important to distinguish these almost-identical methods for the sake of low coupling at the expense of small amounts of duplicate code.




