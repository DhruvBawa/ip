# UI Test Plan

## Configuration

Program command: `java -cp target/classes Larry`
Build command: `mvn -q compile`
Output starts after line: `             ██████████████████████████████████████`

## Test Case: Add and display typed tasks

### Aim

Verify that todo, deadline, and event commands add correctly typed tasks, preserve their details in the list, and update the task count.

### Inputs

```text
todo read book
deadline return book /by June 6th
event project meeting /from Aug 6th 2pm /to 4pm
list
bye
```

### Expected output

```text
         ██████████████████████████████████████████████

I'm EVIL LARRY.
What do you want to do?
    __________________________________________________________
    __________________________________________________________
     Got it. I've added this task:
       [T][ ] read book
     Now you have 1 task in the list.
    __________________________________________________________
    __________________________________________________________
     Got it. I've added this task:
       [D][ ] return book (by: June 6th)
     Now you have 2 tasks in the list.
    __________________________________________________________
    __________________________________________________________
     Got it. I've added this task:
       [E][ ] project meeting (from: Aug 6th 2pm to: 4pm)
     Now you have 3 tasks in the list.
    __________________________________________________________
    __________________________________________________________
     Here are the tasks in your list:
     1.[T][ ] read book
     2.[D][ ] return book (by: June 6th)
     3.[E][ ] project meeting (from: Aug 6th 2pm to: 4pm)
    __________________________________________________________
    __________________________________________________________
     EVIL LARRY has decided to let you go
     FOR NOW...
    __________________________________________________________
```

## Test Case: Mark and unmark a task

### Aim

Verify that a task can be marked as done, reversed to not done, and displayed with the final status.

### Inputs

```text
todo return book
mark 1
unmark 1
list
bye
```

### Expected output

```text
         ██████████████████████████████████████████████

I'm EVIL LARRY.
What do you want to do?
    __________________________________________________________
    __________________________________________________________
     Got it. I've added this task:
       [T][ ] return book
     Now you have 1 task in the list.
    __________________________________________________________
    __________________________________________________________
     Nice! I've marked this task as done:
       [T][X] return book
    __________________________________________________________
    __________________________________________________________
     OK, I've marked this task as not done yet:
       [T][ ] return book
    __________________________________________________________
    __________________________________________________________
     Here are the tasks in your list:
     1.[T][ ] return book
    __________________________________________________________
    __________________________________________________________
     EVIL LARRY has decided to let you go
     FOR NOW...
    __________________________________________________________
```

## Test Case: Delete first, last, and only task

### Aim

Verify that deleting from the front and end of the list updates the count, renumbers remaining tasks, and handles an empty list after deleting the last task.

### Inputs

```text
todo first task
todo second task
todo third task
delete 1
list
delete 2
list
delete 1
list
bye
```

### Expected output

```text
         ██████████████████████████████████████████████

I'm EVIL LARRY.
What do you want to do?
    __________________________________________________________
    __________________________________________________________
     Got it. I've added this task:
       [T][ ] first task
     Now you have 1 task in the list.
    __________________________________________________________
    __________________________________________________________
     Got it. I've added this task:
       [T][ ] second task
     Now you have 2 tasks in the list.
    __________________________________________________________
    __________________________________________________________
     Got it. I've added this task:
       [T][ ] third task
     Now you have 3 tasks in the list.
    __________________________________________________________
    __________________________________________________________
     Noted. I've removed this task:
       [T][ ] first task
     Now you have 2 tasks in the list.
    __________________________________________________________
    __________________________________________________________
     Here are the tasks in your list:
     1.[T][ ] second task
     2.[T][ ] third task
    __________________________________________________________
    __________________________________________________________
     Noted. I've removed this task:
       [T][ ] third task
     Now you have 1 task in the list.
    __________________________________________________________
    __________________________________________________________
     Here are the tasks in your list:
     1.[T][ ] second task
    __________________________________________________________
    __________________________________________________________
     Noted. I've removed this task:
       [T][ ] second task
     Now you have 0 tasks in the list.
    __________________________________________________________
    __________________________________________________________
     Here are the tasks in your list:
    __________________________________________________________
    __________________________________________________________
     EVIL LARRY has decided to let you go
     FOR NOW...
    __________________________________________________________
```

## Test Case: Delete typed task and preserve remaining statuses

### Aim

Verify that deleting a typed task reports the removed item exactly and leaves the remaining task types and done statuses unchanged.

### Inputs

```text
todo read book
deadline return book /by June 6th
event project meeting /from Aug 6th 2pm /to 4pm
todo join sports club
mark 2
mark 4
delete 3
list
bye
```

### Expected output

```text
         ██████████████████████████████████████████████

I'm EVIL LARRY.
What do you want to do?
    __________________________________________________________
    __________________________________________________________
     Got it. I've added this task:
       [T][ ] read book
     Now you have 1 task in the list.
    __________________________________________________________
    __________________________________________________________
     Got it. I've added this task:
       [D][ ] return book (by: June 6th)
     Now you have 2 tasks in the list.
    __________________________________________________________
    __________________________________________________________
     Got it. I've added this task:
       [E][ ] project meeting (from: Aug 6th 2pm to: 4pm)
     Now you have 3 tasks in the list.
    __________________________________________________________
    __________________________________________________________
     Got it. I've added this task:
       [T][ ] join sports club
     Now you have 4 tasks in the list.
    __________________________________________________________
    __________________________________________________________
     Nice! I've marked this task as done:
       [D][X] return book (by: June 6th)
    __________________________________________________________
    __________________________________________________________
     Nice! I've marked this task as done:
       [T][X] join sports club
    __________________________________________________________
    __________________________________________________________
     Noted. I've removed this task:
       [E][ ] project meeting (from: Aug 6th 2pm to: 4pm)
     Now you have 3 tasks in the list.
    __________________________________________________________
    __________________________________________________________
     Here are the tasks in your list:
     1.[T][ ] read book
     2.[D][X] return book (by: June 6th)
     3.[T][X] join sports club
    __________________________________________________________
    __________________________________________________________
     EVIL LARRY has decided to let you go
     FOR NOW...
    __________________________________________________________
```

## Test Case: Reject malformed task creation without changing the list

### Aim

Verify that unknown commands and malformed todo, deadline, and event inputs show Larry's error response and do not add tasks.

### Inputs

```text
todo read book
blah
todo
deadline /by Sunday
deadline return book /by
deadline return book by Sunday
event /from Mon /to Tue
event meeting /from Mon
event meeting /to Tue
event meeting from Mon /to Tue
list
bye
```

### Expected output

```text
         ██████████████████████████████████████████████

I'm EVIL LARRY.
What do you want to do?
    __________________________________________________________
    __________________________________________________________
     Got it. I've added this task:
       [T][ ] read book
     Now you have 1 task in the list.
    __________________________________________________________
    __________________________________________________________
     ERROR!! Fix your inputs Before EVIL LARRY comes after you!
    __________________________________________________________
    __________________________________________________________
     ERROR!! Fix your inputs Before EVIL LARRY comes after you!
    __________________________________________________________
    __________________________________________________________
     ERROR!! Fix your inputs Before EVIL LARRY comes after you!
    __________________________________________________________
    __________________________________________________________
     ERROR!! Fix your inputs Before EVIL LARRY comes after you!
    __________________________________________________________
    __________________________________________________________
     ERROR!! Fix your inputs Before EVIL LARRY comes after you!
    __________________________________________________________
    __________________________________________________________
     ERROR!! Fix your inputs Before EVIL LARRY comes after you!
    __________________________________________________________
    __________________________________________________________
     ERROR!! Fix your inputs Before EVIL LARRY comes after you!
    __________________________________________________________
    __________________________________________________________
     ERROR!! Fix your inputs Before EVIL LARRY comes after you!
    __________________________________________________________
    __________________________________________________________
     ERROR!! Fix your inputs Before EVIL LARRY comes after you!
    __________________________________________________________
    __________________________________________________________
     Here are the tasks in your list:
     1.[T][ ] read book
    __________________________________________________________
    __________________________________________________________
     EVIL LARRY has decided to let you go
     FOR NOW...
    __________________________________________________________
```

## Test Case: Reject invalid task numbers without changing the list

### Aim

Verify that missing, non-numeric, zero, negative, and out-of-range task numbers for mark, unmark, and delete show Larry's error response and preserve the list.

### Inputs

```text
todo read book
mark
mark one
mark 0
mark 2
unmark 2
delete
delete -1
delete 999
list
bye
```

### Expected output

```text
         ██████████████████████████████████████████████

I'm EVIL LARRY.
What do you want to do?
    __________________________________________________________
    __________________________________________________________
     Got it. I've added this task:
       [T][ ] read book
     Now you have 1 task in the list.
    __________________________________________________________
    __________________________________________________________
     ERROR!! Fix your inputs Before EVIL LARRY comes after you!
    __________________________________________________________
    __________________________________________________________
     ERROR!! Fix your inputs Before EVIL LARRY comes after you!
    __________________________________________________________
    __________________________________________________________
     ERROR!! Fix your inputs Before EVIL LARRY comes after you!
    __________________________________________________________
    __________________________________________________________
     ERROR!! Fix your inputs Before EVIL LARRY comes after you!
    __________________________________________________________
    __________________________________________________________
     ERROR!! Fix your inputs Before EVIL LARRY comes after you!
    __________________________________________________________
    __________________________________________________________
     ERROR!! Fix your inputs Before EVIL LARRY comes after you!
    __________________________________________________________
    __________________________________________________________
     ERROR!! Fix your inputs Before EVIL LARRY comes after you!
    __________________________________________________________
    __________________________________________________________
     ERROR!! Fix your inputs Before EVIL LARRY comes after you!
    __________________________________________________________
    __________________________________________________________
     Here are the tasks in your list:
     1.[T][ ] read book
    __________________________________________________________
    __________________________________________________________
     EVIL LARRY has decided to let you go
     FOR NOW...
    __________________________________________________________
```
