# Course Materials
- [6.031: Software Construction-TypeScript](https://web.mit.edu/6.031/www/sp22/)
- [6.005: Software Construction-Java](https://ocw.mit.edu/courses/6-005-software-construction-spring-2016/)
- [6.005: Software Construction-Java](https://ocw.mit.edu/ans7870/6/6.005/s16/)
- [6.005 Github page](https://github.com/mit6005/)
- [Vedio-course]https://www.bilibili.com/video/BV1Tp4y197XX?spm_id_from=333.788.player.switch&vd_source=b3d4057adb36b9b243dc8d7a6fc41295&p=2)
- [CS自学指南](https://csdiy.wiki/%E8%BD%AF%E4%BB%B6%E5%B7%A5%E7%A8%8B/6031/)

# The Goal of 6.005
Our primary goal in this course is learning how to produce software that is:

- Safe from bugs . 
  Correctness (correct behavior right now), and defensiveness (correct behavior in the future).
- Easy to understand . 
 Has to communicate to future programmers who need to understand it and make changes in it (fixing bugs or adding new features). 
That future programmer might be you, months or years from now. You’ll be surprised how much you forget if you don’t write it down, 
and how much it helps your own future self to have a good design.
- Ready for change . 
Software always changes. Some designs make it easy to make changes; others require throwing away and rewriting a lot of code.
There are other important properties of software (like performance, usability, security), and they may trade off against these three. 
- 
But these are the Big Three that we care about in 6.005, and that software developers generally put foremost in the practice of building software. 
It’s worth considering every language feature, every programming practice, every design pattern that we study in this course, 
and understanding how they relate to the Big Three.

## static checking
The main idea we introduced today is static checking . Here’s how this idea relates to the goals of the course:

- Safe from bugs. 
Static checking helps with safety by catching type errors and other bugs before runtime.

- Easy to understand. 
It helps with understanding, because types are explicitly stated in the code.

- Ready for change. 
Static checking makes it easier to change your code by identifying other places that need to change in tandem. 
For example, when you change the name or type of a variable, the compiler immediately displays errors at all the places where that variable is used, reminding you to update them as well.


## Code review
Code review is a widely-used technique for improving software quality by human inspection. Code review can detect many kinds of problems in code, but as a starter, this reading talked about these general principles of good code:

- Don’t Repeat Yourself (DRY)
- Comments where needed
- Fail fast
- Avoid magic numbers
- One purpose for each variable
- Use good names
- No global variables
- Return results, don’t print them
- Use whitespace for readability

The topics of today’s reading connect to our three key properties of good software as follows:

- Safe from bugs. 
In general, code review uses human reviewers to find bugs. DRY code lets you fix a bug in only one place, without fear that it has propagated elsewhere. Commenting your assumptions clearly makes it less likely that another programmer will introduce a bug. The Fail Fast principle detects bugs as early as possible. Avoiding global variables makes it easier to localize bugs related to variable values, since non-global variables can be changed in only limited places in the code.

- Easy to understand. 
Code review is really the only way to find obscure or confusing code, because other people are reading it and trying to understand it. Using judicious comments, avoiding magic numbers, keeping one purpose for each variable, using good names, and using whitespace well can all improve the understandability of code.

- Ready for change. 
Code review helps here when it’s done by experienced software developers who can anticipate what might change and suggest ways to guard against it. DRY code is more ready for change, because a change only needs to be made in one place. Returning results instead of printing them makes it easier to adapt the code to a new purpose.
