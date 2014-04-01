# Assign

Simple tool to assign projects to group of whatever according to their preference.

## How-to

```java
Applications apps = ...
AssignmentProblem ap = new AssignmentProblem(apps);
// customize (like you think it is possible)
Assignment res = ap.compute();
res.printStatistics();
```

## Import/Export

Just a JSON serialisation.

## Sample JSON for an Applications
```json
{
    "subjects": ["s1","s2","s3","s4","s5","s6","s7","s8","s9"],
    "choices": {
        "g1": ["s1","s2","s3","s4"],
        "g2": ["s2","s4","s9","s7"],
        "g3": ["s7","s3","s5","s1"],
    }
}
```

## Sample JSON for an Assignment
```json
{
    "subjects": ["s1","s2","s3","s4","s5","s6","s7","s8","s9"],
    "choices": {
        "g1": "s1,
        "g2": "s2",
        "g3": "s7",
    }
}
```