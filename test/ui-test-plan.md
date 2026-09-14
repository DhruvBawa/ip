# UI Test Plan

## Configuration

Program command: `bash test/run-ui-test.sh`
Build command: `./gradlew -q classes`
Output starts after line: `             ██████████████████████████████████████`

## Test Case: Add and display typed tasks

### Aim

Verify that todo, deadline, and event commands add correctly typed tasks, preserve their details in the list, and update the task count.

### Inputs

```text
todo read book
deadline return book /by 06-06-2026 1800
event project meeting /from 1400 06/08/2026 /to 06/08/2026 16:00
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
     EVIL LARRY has added this task for you:
       [T][ ] read book
     EVIL LARRY says you have 1 task in the list.
    __________________________________________________________
    __________________________________________________________
     EVIL LARRY has added this task for you:
       [D][ ] return book (by: 6 Jun 2026, 6:00 PM)
     EVIL LARRY says you have 2 tasks in the list.
    __________________________________________________________
    __________________________________________________________
     EVIL LARRY has added this task for you:
       [E][ ] project meeting (from: 6 Aug 2026, 2:00 PM to: 6 Aug 2026, 4:00 PM)
     EVIL LARRY says you have 3 tasks in the list.
    __________________________________________________________
    __________________________________________________________
     Here are the tasks EVIL LARRY says are in your list:
     1.[T][ ] read book
     2.[D][ ] return book (by: 6 Jun 2026, 6:00 PM)
     3.[E][ ] project meeting (from: 6 Aug 2026, 2:00 PM to: 6 Aug 2026, 4:00 PM)
    __________________________________________________________
    __________________________________________________________
     EVIL LARRY has decided to let you go
     FOR NOW...
    __________________________________________________________
```

## Test Case: Find tasks by description keyword

### Aim

Verify that find matches description substrings without regard to letter case, supports multi-word phrases, reports no matches, and rejects a missing keyword.

### Inputs

```text
todo read book
deadline return book /by 06-06-2026 1800
event project meeting /from 06-08-2026 1400 /to 06-08-2026 1600
todo buy groceries
mark 1
find BOOK
find project meeting
find exercise
find
bye
```

### Expected output

```text
         ██████████████████████████████████████████████

I'm EVIL LARRY.
What do you want to do?
    __________________________________________________________
    __________________________________________________________
     EVIL LARRY has added this task for you:
       [T][ ] read book
     EVIL LARRY says you have 1 task in the list.
    __________________________________________________________
    __________________________________________________________
     EVIL LARRY has added this task for you:
       [D][ ] return book (by: 6 Jun 2026, 6:00 PM)
     EVIL LARRY says you have 2 tasks in the list.
    __________________________________________________________
    __________________________________________________________
     EVIL LARRY has added this task for you:
       [E][ ] project meeting (from: 6 Aug 2026, 2:00 PM to: 6 Aug 2026, 4:00 PM)
     EVIL LARRY says you have 3 tasks in the list.
    __________________________________________________________
    __________________________________________________________
     EVIL LARRY has added this task for you:
       [T][ ] buy groceries
     EVIL LARRY says you have 4 tasks in the list.
    __________________________________________________________
    __________________________________________________________
     EVIL LARRY has marked this task as done:
       [T][X] read book
    __________________________________________________________
    __________________________________________________________
     EVIL LARRY uncovered these matching tasks:
     1.[T][X] read book
     2.[D][ ] return book (by: 6 Jun 2026, 6:00 PM)
    __________________________________________________________
    __________________________________________________________
     EVIL LARRY uncovered these matching tasks:
     1.[E][ ] project meeting (from: 6 Aug 2026, 2:00 PM to: 6 Aug 2026, 4:00 PM)
    __________________________________________________________
    __________________________________________________________
     EVIL LARRY found nothing. Try a less pathetic keyword.
    __________________________________________________________
    __________________________________________________________
     ERROR: EVIL LARRY needs a keyword before he can hunt. Use: find KEYWORD.
    __________________________________________________________
    __________________________________________________________
     EVIL LARRY has decided to let you go
     FOR NOW...
    __________________________________________________________
```

## Test Case: Understand flexible Singapore date and time inputs

### Aim

Verify that Larry assumes the current year when it is omitted, assumes today for time-only input, and accepts either time-first or date-first input.

### Inputs

```text
deadline submit form /by 06/09 0930
deadline call home /by 21:45
event review /from 0900 07-09 /to 07/09 10:30
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
     EVIL LARRY has added this task for you:
       [D][ ] submit form (by: 6 Sep 2026, 9:30 AM)
     EVIL LARRY says you have 1 task in the list.
    __________________________________________________________
    __________________________________________________________
     EVIL LARRY has added this task for you:
       [D][ ] call home (by: 26 Aug 2026, 9:45 PM)
     EVIL LARRY says you have 2 tasks in the list.
    __________________________________________________________
    __________________________________________________________
     EVIL LARRY has added this task for you:
       [E][ ] review (from: 7 Sep 2026, 9:00 AM to: 7 Sep 2026, 10:30 AM)
     EVIL LARRY says you have 3 tasks in the list.
    __________________________________________________________
    __________________________________________________________
     Here are the tasks EVIL LARRY says are in your list:
     1.[D][ ] submit form (by: 6 Sep 2026, 9:30 AM)
     2.[D][ ] call home (by: 26 Aug 2026, 9:45 PM)
     3.[E][ ] review (from: 7 Sep 2026, 9:00 AM to: 7 Sep 2026, 10:30 AM)
    __________________________________________________________
    __________________________________________________________
     EVIL LARRY has decided to let you go
     FOR NOW...
    __________________________________________________________
```

## Test Case: Show deadlines and events occurring on a date

### Aim

Verify that the on command displays deadlines and events occurring on the requested date, includes multi-day events, excludes todos, and retains original task numbers.

### Inputs

```text
todo prepare notes
deadline submit form /by 06-09-2026 0930
event conference /from 05-09-2026 0900 /to 07-09-2026 1700
deadline later task /by 08-09-2026 1200
on 06/09
on 09-09-2026
bye
```

### Expected output

```text
         ██████████████████████████████████████████████

I'm EVIL LARRY.
What do you want to do?
    __________________________________________________________
    __________________________________________________________
     EVIL LARRY has added this task for you:
       [T][ ] prepare notes
     EVIL LARRY says you have 1 task in the list.
    __________________________________________________________
    __________________________________________________________
     EVIL LARRY has added this task for you:
       [D][ ] submit form (by: 6 Sep 2026, 9:30 AM)
     EVIL LARRY says you have 2 tasks in the list.
    __________________________________________________________
    __________________________________________________________
     EVIL LARRY has added this task for you:
       [E][ ] conference (from: 5 Sep 2026, 9:00 AM to: 7 Sep 2026, 5:00 PM)
     EVIL LARRY says you have 3 tasks in the list.
    __________________________________________________________
    __________________________________________________________
     EVIL LARRY has added this task for you:
       [D][ ] later task (by: 8 Sep 2026, 12:00 PM)
     EVIL LARRY says you have 4 tasks in the list.
    __________________________________________________________
    __________________________________________________________
     EVIL LARRY says these tasks occur on 6 Sep 2026:
     2.[D][ ] submit form (by: 6 Sep 2026, 9:30 AM)
     3.[E][ ] conference (from: 5 Sep 2026, 9:00 AM to: 7 Sep 2026, 5:00 PM)
    __________________________________________________________
    __________________________________________________________
     EVIL LARRY found no tasks on 9 Sep 2026. Your reprieve is temporary.
    __________________________________________________________
    __________________________________________________________
     EVIL LARRY has decided to let you go
     FOR NOW...
    __________________________________________________________
```

## Test Case: Exercise automatic saving after task changes

### Aim

Exercise add, mark, and delete operations that save every task type, including field-separator and backslash characters in descriptions, while preserving the console UI.

### Inputs

```text
todo read | book \ notes
deadline return | book /by 06-06-2026 1800
event project | meeting \ notes /from 06-08-2026 1400 /to 06-08-2026 1600
todo temporary task
mark 1
delete 4
bye
```

### Expected output

```text
         ██████████████████████████████████████████████

I'm EVIL LARRY.
What do you want to do?
    __________________________________________________________
    __________________________________________________________
     EVIL LARRY has added this task for you:
       [T][ ] read | book \ notes
     EVIL LARRY says you have 1 task in the list.
    __________________________________________________________
    __________________________________________________________
     EVIL LARRY has added this task for you:
       [D][ ] return | book (by: 6 Jun 2026, 6:00 PM)
     EVIL LARRY says you have 2 tasks in the list.
    __________________________________________________________
    __________________________________________________________
     EVIL LARRY has added this task for you:
       [E][ ] project | meeting \ notes (from: 6 Aug 2026, 2:00 PM to: 6 Aug 2026, 4:00 PM)
     EVIL LARRY says you have 3 tasks in the list.
    __________________________________________________________
    __________________________________________________________
     EVIL LARRY has added this task for you:
       [T][ ] temporary task
     EVIL LARRY says you have 4 tasks in the list.
    __________________________________________________________
    __________________________________________________________
     EVIL LARRY has marked this task as done:
       [T][X] read | book \ notes
    __________________________________________________________
    __________________________________________________________
     EVIL LARRY removed this task:
       [T][ ] temporary task
     EVIL LARRY says you have 3 tasks in the list.
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
     EVIL LARRY has added this task for you:
       [T][ ] return book
     EVIL LARRY says you have 1 task in the list.
    __________________________________________________________
    __________________________________________________________
     EVIL LARRY has marked this task as done:
       [T][X] return book
    __________________________________________________________
    __________________________________________________________
     EVIL LARRY has marked this task as not done yet:
       [T][ ] return book
    __________________________________________________________
    __________________________________________________________
     Here are the tasks EVIL LARRY says are in your list:
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
     EVIL LARRY has added this task for you:
       [T][ ] first task
     EVIL LARRY says you have 1 task in the list.
    __________________________________________________________
    __________________________________________________________
     EVIL LARRY has added this task for you:
       [T][ ] second task
     EVIL LARRY says you have 2 tasks in the list.
    __________________________________________________________
    __________________________________________________________
     EVIL LARRY has added this task for you:
       [T][ ] third task
     EVIL LARRY says you have 3 tasks in the list.
    __________________________________________________________
    __________________________________________________________
     EVIL LARRY removed this task:
       [T][ ] first task
     EVIL LARRY says you have 2 tasks in the list.
    __________________________________________________________
    __________________________________________________________
     Here are the tasks EVIL LARRY says are in your list:
     1.[T][ ] second task
     2.[T][ ] third task
    __________________________________________________________
    __________________________________________________________
     EVIL LARRY removed this task:
       [T][ ] third task
     EVIL LARRY says you have 1 task in the list.
    __________________________________________________________
    __________________________________________________________
     Here are the tasks EVIL LARRY says are in your list:
     1.[T][ ] second task
    __________________________________________________________
    __________________________________________________________
     EVIL LARRY removed this task:
       [T][ ] second task
     EVIL LARRY says you have 0 tasks in the list.
    __________________________________________________________
    __________________________________________________________
     EVIL LARRY's task vault is empty. Enjoy your freedom while it lasts.
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
deadline return book /by 06-06-2026 1800
event project meeting /from 06-08-2026 1400 /to 06-08-2026 1600
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
     EVIL LARRY has added this task for you:
       [T][ ] read book
     EVIL LARRY says you have 1 task in the list.
    __________________________________________________________
    __________________________________________________________
     EVIL LARRY has added this task for you:
       [D][ ] return book (by: 6 Jun 2026, 6:00 PM)
     EVIL LARRY says you have 2 tasks in the list.
    __________________________________________________________
    __________________________________________________________
     EVIL LARRY has added this task for you:
       [E][ ] project meeting (from: 6 Aug 2026, 2:00 PM to: 6 Aug 2026, 4:00 PM)
     EVIL LARRY says you have 3 tasks in the list.
    __________________________________________________________
    __________________________________________________________
     EVIL LARRY has added this task for you:
       [T][ ] join sports club
     EVIL LARRY says you have 4 tasks in the list.
    __________________________________________________________
    __________________________________________________________
     EVIL LARRY has marked this task as done:
       [D][X] return book (by: 6 Jun 2026, 6:00 PM)
    __________________________________________________________
    __________________________________________________________
     EVIL LARRY has marked this task as done:
       [T][X] join sports club
    __________________________________________________________
    __________________________________________________________
     EVIL LARRY removed this task:
       [E][ ] project meeting (from: 6 Aug 2026, 2:00 PM to: 6 Aug 2026, 4:00 PM)
     EVIL LARRY says you have 3 tasks in the list.
    __________________________________________________________
    __________________________________________________________
     Here are the tasks EVIL LARRY says are in your list:
     1.[T][ ] read book
     2.[D][X] return book (by: 6 Jun 2026, 6:00 PM)
     3.[T][X] join sports club
    __________________________________________________________
    __________________________________________________________
     EVIL LARRY has decided to let you go
     FOR NOW...
    __________________________________________________________
```

## Test Case: Reject malformed task creation without changing the list

### Aim

Verify that unknown commands and malformed todo, deadline, and event inputs show actionable responses in Larry's voice and do not add tasks.

### Inputs

```text
todo read book
blah
todo
deadline /by Sunday
deadline return book /by
deadline return book by Sunday
deadline impossible date /by 30-02-2026 1200
event /from Mon /to Tue
event meeting /from Mon
event meeting /to Tue
event meeting from Mon /to Tue
event invalid end /from 06-08-2026 1400 /to tomorrow
event backwards /from 12-09-2026 1000 /to 12-09-2026 0900
on
on 2026-09-06
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
     EVIL LARRY has added this task for you:
       [T][ ] read book
     EVIL LARRY says you have 1 task in the list.
    __________________________________________________________
    __________________________________________________________
     ERROR: EVIL LARRY rejects that command. Use todo, deadline, event, list, mark, unmark, delete, find, on, or bye.
    __________________________________________________________
    __________________________________________________________
     ERROR: EVIL LARRY cannot bind a nameless task. Use: todo DESCRIPTION.
    __________________________________________________________
    __________________________________________________________
     ERROR: EVIL LARRY demands proper tribute. Use: deadline DESCRIPTION /by DATE TIME.
    __________________________________________________________
    __________________________________________________________
     ERROR: EVIL LARRY demands proper tribute. Use: deadline DESCRIPTION /by DATE TIME.
    __________________________________________________________
    __________________________________________________________
     ERROR: EVIL LARRY demands proper tribute. Use: deadline DESCRIPTION /by DATE TIME.
    __________________________________________________________
    __________________________________________________________
     ERROR: EVIL LARRY rejects that deadline date. Try: 06-09-2026 1800.
    __________________________________________________________
    __________________________________________________________
     ERROR: EVIL LARRY demands a complete scheme. Use: event DESCRIPTION /from DATE TIME /to DATE TIME.
    __________________________________________________________
    __________________________________________________________
     ERROR: EVIL LARRY demands a complete scheme. Use: event DESCRIPTION /from DATE TIME /to DATE TIME.
    __________________________________________________________
    __________________________________________________________
     ERROR: EVIL LARRY demands a complete scheme. Use: event DESCRIPTION /from DATE TIME /to DATE TIME.
    __________________________________________________________
    __________________________________________________________
     ERROR: EVIL LARRY demands a complete scheme. Use: event DESCRIPTION /from DATE TIME /to DATE TIME.
    __________________________________________________________
    __________________________________________________________
     ERROR: EVIL LARRY rejects that event date. Try: 06-09-2026 1400.
    __________________________________________________________
    __________________________________________________________
     ERROR: EVIL LARRY refuses to bend time. An event must end after it starts.
    __________________________________________________________
    __________________________________________________________
     ERROR: EVIL LARRY needs a date to inspect. Use: on DATE.
    __________________________________________________________
    __________________________________________________________
     ERROR: EVIL LARRY cannot rule that date. Try: on 06-09-2026.
    __________________________________________________________
    __________________________________________________________
     Here are the tasks EVIL LARRY says are in your list:
     1.[T][ ] read book
    __________________________________________________________
    __________________________________________________________
     EVIL LARRY has decided to let you go
     FOR NOW...
    __________________________________________________________
```

## Test Case: Reject invalid task numbers without changing the list

### Aim

Verify that missing, non-numeric, zero, negative, and out-of-range task numbers show actionable responses in Larry's voice and preserve the list.

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
     EVIL LARRY has added this task for you:
       [T][ ] read book
     EVIL LARRY says you have 1 task in the list.
    __________________________________________________________
    __________________________________________________________
     ERROR: EVIL LARRY demands a positive task number after mark.
    __________________________________________________________
    __________________________________________________________
     ERROR: EVIL LARRY demands a positive task number after mark.
    __________________________________________________________
    __________________________________________________________
     ERROR: EVIL LARRY demands a positive task number after mark.
    __________________________________________________________
    __________________________________________________________
     ERROR: EVIL LARRY cannot find that task number in his vault.
    __________________________________________________________
    __________________________________________________________
     ERROR: EVIL LARRY cannot find that task number in his vault.
    __________________________________________________________
    __________________________________________________________
     ERROR: EVIL LARRY demands a positive task number after delete.
    __________________________________________________________
    __________________________________________________________
     ERROR: EVIL LARRY demands a positive task number after delete.
    __________________________________________________________
    __________________________________________________________
     ERROR: EVIL LARRY cannot find that task number in his vault.
    __________________________________________________________
    __________________________________________________________
     Here are the tasks EVIL LARRY says are in your list:
     1.[T][ ] read book
    __________________________________________________________
    __________________________________________________________
     EVIL LARRY has decided to let you go
     FOR NOW...
    __________________________________________________________
```
