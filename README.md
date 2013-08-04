play-form-kludge
================

![screenshot](https://raw.github.com/philipmjohnson/play-form-kludge/master/doc/play-form-kludge-homepage.png)


Overview
--------

I began development of this application to provide an example of form processing slightly different 
from the play example programs (forms and computer database) that would demonstrate the following 
requirements:

  * Use of Twitter Bootstrap.
  * Use of multi-valued form controls (checkboxes and multi-select)
  * Use of a backing entity with nested entity and List of entity fields.
  * Validation.
  
**First attempt: Use the Play helper classes, form binding/unbinding, and validation**

My first attempt at implementing this application failed due an inability to create a multi-valued 
form control using checkboxes that would correctly render and bind values. I believe the 
failure of this first attempt is due to one of the following:

  * Form processing in Play is not documented well enough to cover this use case.
  * Form processing in Play is not designed well enough to cover this use case. 
  
As a newcomer to Play, I realize that this second hypothesis is somewhat presumptuous, but it is
based upon the following two observations:

First, the framework did not provide helpful feedback during my attempts to implement the checkbox-based form
control.  Almost all attempts resulted in the framework either failing silently to perform the binding,
or else throwing an internal exception.  In neither case did the framework provide guidance about
what I did wrong or what to try next.

Second, the Scala template code in views.helper is pretty opaque for Java Play developers
with only elementary understanding of Scala.   In [Play for Java](http://www.manning.com/leroux/),
the authors claim that only a simple understanding of Scala (i.e. for loops, conditionals,
and variable substitution) is necessary.  I disagree.  The Scala template code is a nice demonstration
of the language's capabilities for composability, but it comes at the cost of readability for 
those who are not proficient in Scala.  

**Second attempt: Manually implement helper classes, form binding/unbinding, and validation**

After coming to the conclusion that I might never succeed in implementing my requirements using the built-in Play 
facilities, I decided to abandon the built-in features of Play
for form processing and code it all manually.  This turned out to be straightforward and
I had a working system within a couple of days.  This code is available in the master
branch of this repo.  

In my opinion, the strengths of the current (manual) implementation are:  (1) The [views.helper template code](https://github.com/philipmjohnson/play-form-kludge/tree/master/app/views/helper) is 
trivially simple to read, debug, and extend for Scala novices; (2) The approach failed gracefully 
during development: when I coded a feature incorrectly, it was always easy to debug.

The weaknesses are: (1) The binding/validation code (in [Student.makeInstance](https://github.com/philipmjohnson/play-form-kludge/blob/master/app/models/Student.java#L40-119)) is a hot mess, and does
not use annotation-based validation which is clearly superior; (2) doing the
form binding and unbinding by hand just seems lame for a framework as elegant as Play; and 
(3) the built-in code seems like it provides additional functionality (such as the "lang" property).

The challenge
-------------

I believe this application provides an opportunity to improve the community's understanding of
Play form processing.  I would like to request that Play developers clone this repository, 
make the changes necessary to correctly implement form processing using the built-in 
capabilities, and then issue a pull request back to me with their changes.  I will then publish
the revised versions as branches of this repo.   

Since the form itself is quite simple, and because the model and controller code should require minimal if any 
changes, my expectation is that an experienced Play form developer can make the required changes
to the views package in a short time with very little effort. The resulting versions of the same system will,
I hope, provide new and valuable documentation about form processing in Play that will enable
future newcomers to avoid the problems I encountered.

**Issues for reimplementers**

  * It seems to me that one implicitFieldConstructor is not enough for this application, as the 
    text, checkbox, and radio button controllers all require different syntax.  In my version,
    I abandoned 3 "implicit" field constructors for 4 "explicit" field constructors that directly
    code the Bootstrap syntax appropriate for the controller. I will be interested to see which
    approach is ultimately simpler and more readable.
    
  * I will be interested to see how binding of form elements to entities is accomplished in the
    case of embedded entities. In [Play for Java](http://www.manning.com/leroux/), the example code does a "two pass" binding: first the    
    framework binds the form fields to "bogus" entity instances, then the application code has to
    do another pass to get valid entities.  My manual implementation avoids this problem.  Perhaps
    there is a cleaner way.
    
  * The model entities all have private fields and explicit setters and getters.  I recognize that 
    the Play community thinks this is avoidable Java verbosity, but the implicit creation of getters and 
    setters has [well documented problems](http://www.manning-sandbox.com/thread.jspa?messageID=143570&#143570).
    So please do not spend time improving this sample code by getting rid of the getters and setters.

  * Why no tests?   It would have been cool to provide automated testing, but I was unsure of what
    tests would run in a manner that could adequately assess the correctness of the reimplementation 
    and also not require changes themselves as part of a reimplementation. So, I bailed.
    
Playing with the application
----------------------------

You can play with a live version of the application at: http://play-form-kludge.philipmjohnson.cloudbees.net

To prefill the form with valid values, retrieve the following URLs:

  * http://play-form-kludge.philipmjohnson.cloudbees.net?id=1
  * http://play-form-kludge.philipmjohnson.cloudbees.net?id=2

Press submit, then look below the form to see the valid Student instance that was constructed.

To prefill the form with invalid values, retrieve the following URLs:

  * http://play-form-kludge.philipmjohnson.cloudbees.net?id=3
  * http://play-form-kludge.philipmjohnson.cloudbees.net?id=4

Press submit, then look below the form to see the resulting (invalid) Student instance.
    








