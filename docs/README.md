# EVIL LARRY User Guide

EVIL LARRY is a desktop task manager for people who prefer typing short commands
to clicking through menus. It keeps todos, deadlines, and events in one list and
saves every change automatically.

![EVIL LARRY showing a task list and several commands](Ui.png)

## Quick start

1. Install Java 25.
1. Download `larry.jar` from the
   [latest release](https://github.com/DhruvBawa/ip/releases/latest).
1. Place the JAR in a folder where you want EVIL LARRY to keep its data.
1. Open a terminal in that folder and run:

   ```bash
   java -jar larry.jar
   ```

1. Type a command in the box at the bottom of the window, then press **Enter** or
   click **Send**.

EVIL LARRY stores your tasks in `data/larry.txt` beside the JAR. The file and
folder are created automatically, and saved tasks are loaded the next time the
application starts.

## Reading the task list

Each task has a type and a status:

- `[T]` is a todo, `[D]` is a deadline, and `[E]` is an event.
- `[ ]` means incomplete, while `[X]` means complete.
- The number before a task is used by commands such as `mark` and `delete`.

For example, `2.[D][X] return book (by: 6 Sep 2026, 6:00 PM)` is a completed
deadline with task number 2.

## Date and time formats

Dates use **day-month-year** order. Use either `-` or `/` as the separator:
`06-09-2026` or `06/09/2026`. You may omit the year to use the current year,
for example `06/09`.

Times use the 24-hour clock, with or without a colon: `1800` or `18:00`. For a
deadline or event time, you may put the date before or after the time. A time on
its own uses today's date.

Examples: `06-09-2026 1800`, `18:00 06/09/2026`, `06/09 1800`, `1800`.

## Features

### Adding a todo: `todo`

Adds a task without a date or time.

Format: `todo DESCRIPTION`

Example: `todo read book`

### Adding a deadline: `deadline`

Adds a task that must be completed by a particular time.

Format: `deadline DESCRIPTION /by DATE TIME`

Example: `deadline submit report /by 18-09-2026 2359`

### Adding an event: `event`

Adds a task with a start and end time. The event must end after it starts.

Format: `event DESCRIPTION /from DATE TIME /to DATE TIME`

Example: `event project meeting /from 18-09-2026 1400 /to 18-09-2026 1600`

### Listing all tasks: `list`

Shows every saved task and its task number.

Format: `list`

### Finding tasks by description: `find`

Shows tasks whose descriptions contain the given keyword or phrase. Matching is
not case-sensitive.

Format: `find KEYWORD`

Example: `find project meeting`

> [!TIP]
> Run `list` before using a task number from search results. Search results are
> numbered by result order, while task-changing commands use the full list's
> task numbers.

### Viewing tasks on a date: `on`

Shows deadlines due on the date and events that overlap the date. Todos are not
shown.

Format: `on DATE`

Example: `on 18/09/2026`

### Marking a task as complete: `mark`

Marks the task with the given number as complete.

Format: `mark TASK_NUMBER`

Example: `mark 2`

### Marking a task as incomplete: `unmark`

Marks the task with the given number as incomplete again.

Format: `unmark TASK_NUMBER`

Example: `unmark 2`

### Deleting a task: `delete`

Permanently removes the task with the given number. Remaining tasks are
renumbered.

Format: `delete TASK_NUMBER`

Example: `delete 2`

### Exiting the application: `bye`

Closes EVIL LARRY after a short farewell.

Format: `bye`

## Command summary

| Action | Command |
| --- | --- |
| Add a todo | `todo DESCRIPTION` |
| Add a deadline | `deadline DESCRIPTION /by DATE TIME` |
| Add an event | `event DESCRIPTION /from DATE TIME /to DATE TIME` |
| List all tasks | `list` |
| Find tasks | `find KEYWORD` |
| View tasks on a date | `on DATE` |
| Mark a task complete | `mark TASK_NUMBER` |
| Mark a task incomplete | `unmark TASK_NUMBER` |
| Delete a task | `delete TASK_NUMBER` |
| Exit | `bye` |
