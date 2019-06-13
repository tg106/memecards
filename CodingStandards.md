## Line Length
Use the default Android Studio vertical line.

## Commenting
Whenever possible, `methods` should be documented with a multiline comment above its signature to explain its purpose.

    /* foo()
     *
     * purpose: Purpose of the method
     */ 

## Naming conventions

`variables`, `methods`, `classes` and `interfaces` use __camelCase__.

    myVariable
    myMethod 
    MyClass
    MyInterface

`Constants` are all __uppercase__ with words separated with an __underscore__. 

    MY_CONSTANT

## { Braces }
The __opening brace__ is on the __same line__ as the method’s signature, the class name, the conditional statement, etc.

The __closing brace__ is on its __own line__, with the exception of simple `accessors` and `mutators`.

    public String myMethod(int x) {
        String s = “I am The Count”;
        for(int i = 0; i < x; i++) {
            s += “ ha”;
        }
        return s;
    }

    public String getSomething() { return this.something; }
 
## Package and Import Statements
First __non-comment line__ in the file is a __package statement__
After, __import__ statements can follow.
 
    package java.awt;
 
    import java.awt.peer.CanvasPeer;

## File Structure
Android's guidelines can be found [here](https://developer.android.com/guide/topics/resources/providing-resources).


