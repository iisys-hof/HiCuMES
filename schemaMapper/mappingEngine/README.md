# Mapping

We have a mapper that could map different dateformat into each other.

# Workflow

 * Read Input
 * Parse Input
 * Execute Mapping
 * Save Mapping

## Read Input

We are able to implement different Inputs e.g. File, Http Server, ...
The reader is independent from the input format. e.g. CSV or JSON file.

## Parse Input

Every input has to been pared to a JSON format.

# Mapping
This is an example mapping. The UI generate the mapping in this format. Here every loop is explicit available
The format is hard connected to the mapping engine. The format should not bee changed, only extended.
```JSON
{
    "mappings": [
        "output.ordering.orderingId = input.LOIPRO04.IDOC.E1AFKOL.AUFNR",
        "output.ordering.totalQuantityUnit = input.LOIPRO04.IDOC.E1AFKOL.BMENGE"
    ],
    "loops": [
        {
            "mappings": [
                "output.component = input.ATNAM",
                "output.description = logging(nullIfError(\"input.E1VCCHT[0].ATBEZ\")) //Dies funktioniert nicht, da immer unterschiedlich viele Sprachen vorhanden sind.",
                "output.description = logging(nullIfError('_.find(input.E1VCCHT, {\"LAISO\": \"DE\"}).ATBEZ'))"
            ],
            "inputSelector": "input.LOIPRO04.IDOC.E1AFKOL.E1VCCHR",
            "outputSelector": "output.ordering.orderingPartsListList"
        }
    ]
}
```

# Save Mapping

With saving, we can save the data to the database.