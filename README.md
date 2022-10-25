**Hello World**, welcome to my article related to mutation testing.

## Special thanks:

Before we start, I would like to thank my friend [**Paulo Ricardo**](https://www.linkedin.com/in/pricardoti/) for the encouragement of writing this article and being able to share my vision of this tool with the rest of you.

## What is Mutation Testing anyway?

![image](https://dev-to-uploads.s3.amazonaws.com/uploads/articles/74xrpgja3vl50ijpvxfx.png)

Pitest makes a mutation in your project, it allows you to guarantee a real margin of coverage within your tests by making a “copy” of the project and inserting errors to see if your tests will fail after the mutation. When the test fails the mutant is killed. If any mutant survives this means you need to do more unit tests, the live mutants serve as input to create more tests.

## Okay, but how does this help me with the quality of my code?

![image](https://dev-to-uploads.s3.amazonaws.com/uploads/articles/lswmr2pp6tnea5hs8k1n.png)

Due to the classic rush that developers need to get into to deliver a particular project, testing is done more in order to increase code coverage, but this is not correct, testing comes with the objective of providing more quality that you are delivering to a client or consumer and also documenting so that another developer can better understand your code, later on you will understand where I want to go :)

## What technologies will we use?

![image](https://dev-to-uploads.s3.amazonaws.com/uploads/articles/gwddh9mzr7fub83gdvag.png)

For this article, we will need to have the following technologies in our project:

1. [Java 8+](https://www.java.com/pt-BR/download/help/whatis_java.html): Java Programming Language in version 8+
2. [Junit 5](https://junit.org/junit5/): Test development framework
3. [Maven](https://maven.apache.org/): Build automation tool
4. [Pitest](https://pitest.org/): Tool to perform mutant tests
5. [Spring](https://spring.io/): programming framework

I will not go into too much into all the technologies used because it is not our purpose of this article.

## Setting up our project:

![image](https://dev-to-uploads.s3.amazonaws.com/uploads/articles/vc5dbouy55a2xq6iyn5z.png)

In order for us to use **pitest**, we need to use Junit 4 or higher, but as mentioned earlier, we will use **Junit5**:

```xml
<dependency>
    <groupId>org.junit.platform</groupId>
    <artifactId>junit-platform-launcher</artifactId>
    <version>1.7.1</version>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter-engine</artifactId>
    <version>5.7.1</version>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>org.junit.vintage</groupId>
    <artifactId>junit-vintage-engine</artifactId>
    <version>5.7.1</version>
    <scope>test</scope>
</dependency>
```
It will also be necessary for us to add the **pitest** plugin to our project, which is the main objective of our article:

```xml
            <plugin>
                <groupId>org.pitest</groupId>
                <artifactId>pitest-maven</artifactId>
                <version>1.4.11</version>
                <dependencies>
                    <dependency>
                        <groupId>org.pitest</groupId>
                        <artifactId>pitest-junit5-plugin</artifactId>
                        <version>0.8</version>
                    </dependency>
                </dependencies>
                <configuration>
                    <targetClasses>
                        <param>YourClassPackage.*</param>
                    </targetClasses>
                    <targetTests>
                        <param>YourTestPackage.*</param>
                    </targetTests>
                </configuration>
            </plugin>
```
**Note**: There are other settings that you can insert in the plugin according to your preference, such as excluding a certain class or package that will not be tested:

```xml
<excludedClasses>
 <param>PackageOfYourClasses.*</param>
 <param>PackageOfYourClasses.IamAClass</param>
</excludedClasses>
```
I just hope you don't delete the entire project, ok? :D

## Revealing the failure to test for coverage only:

![image](https://dev-to-uploads.s3.amazonaws.com/uploads/articles/9txnkmi5av2hmkoojqoq.png)

As mentioned earlier, due to some rush or even lack of knowledge, the developer usually does the test just for coverage or ends up forgetting to insert some important validation, so let's analyze the following scenario:

- Inside our project we have a class called **UserFactory**

- In our method we will receive as parameters a String(name) and an Integer(year)

- After that we will make the instance of the **User** class and we will set the parameters received in their respective attributes

- Soon after, we will return the user

```java
public final class UserFactory {

    private UserFactory() {}
    
    public static User create(final String name, final Integer year) {
        final User user = new User();
        
        user.setName(name);
        user.setYear(year);
        
        return user;
    }
}
```

Okay, after we understand the purpose of our method, let's go to our test class, **UserFactoryTest**:

```java
class UserFactoryTest {

    @Test
    @DisplayName("Should return a User - When success")
    void create_User_WhenSuccess() {
        final Integer year = 10;
        final String name = "Dudu";

        final User user = UserFactory.create(name, year);

        Assertions.assertNotNull(user);
        Assertions.assertEquals(name, user.getName());
    }
}
```

As we can see, we made the method call of our class and made the following checks:
- We check that the return object is not null
- We verified that *expected name** is equal to the name entered in **user**

Shall we check out our coverage?

![Image description](https://dev-to-uploads.s3.amazonaws.com/uploads/articles/tg8skzk1lhyiqk87i88e.png)

But after all, in this simple example we had no problem, where was our mistake?

Exactly, we forgot to do the validation if the **year** attribute was set correctly, let's see what the **Pitest** has to say?

## Getting more into the tool:

![image](https://dev-to-uploads.s3.amazonaws.com/uploads/articles/8teleik9kxgyusxvhci8.png)

So that we can run **Pitest** just run the following command in your project's terminal:

```xml
mvn org.pitest:pitest-maven:mutationCoverage

```

Or run the plugin directly in your IDE by clicking on the **pitest:mutationCoverage** option, the choice is yours :)

![Captura de tela de 2021-03-16 23-41-57](https://dev-to-uploads.s3.amazonaws.com/uploads/articles/h1ke2dm7umue1kxfbvbq.png)

After executing the execution and having correctly configured your project so far, you will get the following result:

![Image description](https://dev-to-uploads.s3.amazonaws.com/uploads/articles/pt213dellzng60cqtb8i.png)

As we can see above, the **Pitest** generated 5 mutations and with our test we managed to kill only **3**, but don't worry, so that you have a more analytical view, the **Pitest* * generates a report for you, so that we can access it, just follow the following path:

- target
- pit-reports
- index.html


![Image description](https://dev-to-uploads.s3.amazonaws.com/uploads/articles/7n3kullyohu6yahro7vk.png)

As we can see, **Pitest** took our line from user.setYear(year); and said that if we remove the call from that line, the result will be the same and we know this is not true, correct? :)

So now we just go back to our test class and make the necessary changes.

## Thank you guys:

![image](https://dev-to-uploads.s3.amazonaws.com/uploads/articles/utjsstdcrxflznbq5mx9.png)

Thanks to all of you for reading my article, as I said this was the first of many to come, so if you liked it or have a comment so you can be supporting and encouraging me I will be extremely grateful :)

Now I leave a special challenge for you to run **Pitest** on your project and tell me what was your reaction when you saw the result :D

**Note:** My first time was not very positive, but it helped me to have a higher quality in the projects :D

- Linkedin: https://www.linkedin.com/in/gabriel-augusto-1b4914145/
- Dev.to: https://dev.to/gabrielaugusto1996/testes-de-mutacao-garanta-ja-a-qualidade-do-seu-codigo-16gn
 
