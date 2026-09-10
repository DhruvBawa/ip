# Larry User Guide

Larry stores todos, deadlines, and events through a lowercase,
case-sensitive command interface. Task numbers shown by `list` start at 1.

## Adding tasks

Use one of these commands:

```text
todo DESCRIPTION
deadline DESCRIPTION /by DATE_TIME
event DESCRIPTION /from DATE_TIME /to DATE_TIME
```

For example:

```text
todo read the chapter
deadline submit report /by 10-9-2026 1800
event project meeting /from 11-9-2026 1400 /to 11-9-2026 1600
```

An event must end strictly after it starts. An event with equal endpoints or
an end before its start is rejected.

## Date and time input

Larry accepts 24-hour times with or without a colon, such as `0930`, `9:30`,
and `21:45`. A date uses day-month-year order with hyphens or slashes, such as
`6-9-2026` or `06/09/2026`.

The year may be omitted, in which case Larry uses the current year. A date may
be omitted too, in which case Larry uses today. When both are supplied, either
order is accepted:

```text
6-9-2026 0930
0930 6/9/2026
```

## Editing one task field

The `edit` command changes exactly one detail of an existing task:

```text
edit INDEX /description DESCRIPTION
edit INDEX /by DATE_TIME
edit INDEX /from DATE_TIME
edit INDEX /to DATE_TIME
```

The available field depends on the task type:

| Field | Allowed task type | Example |
| --- | --- | --- |
| `/description` | Todo, deadline, or event | `edit 1 /description read chapters 2 and 3` |
| `/by` | Deadline only | `edit 2 /by 12-9-2026 1800` |
| `/from` | Event only | `edit 3 /from 14-9-2026 1330` |
| `/to` | Event only | `edit 3 /to 14-9-2026 1600` |

Larry reports both versions after a successful edit:

```text
EVIL LARRY updated task 3:
Before: [E][ ] project meeting (from: 14 Sep 2026, 2:00 PM to: 14 Sep 2026, 4:00 PM)
After:  [E][ ] project meeting (from: 14 Sep 2026, 1:30 PM to: 14 Sep 2026, 4:00 PM)
```

The task keeps its type, completion status, list position, and every field not
named in the command. Supplying the current value again is also a successful
edit. Everything after `/description` is description text, so a description
may itself contain text such as `/by` or `/to`.

Larry rejects an edit when:

- the index is missing, non-numeric, zero, negative, or outside the task list;
- the field marker is missing or is not one of the four markers above;
- the replacement is blank;
- the field does not apply to that task type;
- a date-time value is invalid; or
- an event edit would make its end equal to or earlier than its start.

Validation happens before a field is changed, so a rejected edit leaves the
task unchanged. Successful edits are saved immediately.

Older saved events can contain free-form date text that Larry cannot parse.
Such an endpoint can be replaced normally. Larry allows the two endpoints to
be repaired one at a time and enforces their order as soon as both are
parseable.

## Viewing and finding tasks

Use `list` to show every task, `find KEYWORD` to search descriptions without
regard to letter case, and `on DATE` to show deadlines and events occurring on
a date.

## Updating status and deleting tasks

Use `mark INDEX` and `unmark INDEX` to change completion status. Use
`delete INDEX` to remove a task. Editing does not replace these operations and
cannot change a task's status or type.

Enter `bye` to exit Larry.
