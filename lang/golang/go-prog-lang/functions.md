Functions
=========

## Errors

Us ual ly when a function retur ns a non-ni l er ror, its other results are undefine d and should be ig nored.

### Error-Handling Strategies

 1. Propagate the error to the caller.

```golang
doc, err := html.Parse(resp.Body)
resp.Body.Close()
if err != nil {
    return nil, fmt.Errorf("parsing %s as HTML: %v", url, err) // wrap error before propagating
}
```

Becaus e er ror messages are fre quently chained toget her, message str ings should not be capit al- ize d and newlines should be avoided.

In general, the cal l f(x) is responsible for rep orting the att emp ted operat ion f and
the argu-ment value x as the y re late to the context of the error.
The cal ler is responsible for adding further infor mat ion that it has but the cal l f(x) do es not.

 2. For error s that represent transient or unpredic table pro blems, it may make sense to re try the fai le d op erat ion, 
    possibly wit h a del ay between tries, and perhaps wit h a limit on the number of att emp ts or the time spent trying
    before giv ing up ent ire ly.

```golang
func WaitForServer(url string) error {
    const timeout = 1 * time.Minute
    deadline := time.Now().Add(timeout)
    for tries := 0; time.Now().Before(deadline); tries++ {
        _, err := http.Head(url)
        if err == nil {
            return nil // success
        }
        log.Printf("server not responding (%s); retrying...", err)
        time.Sleep(time.Second << uint(tries)) // exponential back-off
    }
    return fmt.Errorf("server %s failed to respond after %s", url, timeout)
}
```

 3. If progress is imp ossible, the cal ler can print the error and stop the program gracef ully,
    but this cours e of action should general ly be res erve d for the main package of a program.
    Librar y func tions should usu ally pro pagate error s to the cal ler, 
    unless the error is a sig n of an internal inconsistenc y—that is, a bug .

```golang
// (In function main.)
if err := WaitForServer(url); err != nil {
    fmt.Fprintf(os.Stderr, "Site is down: %v\n", err)
    os.Exit(1)
}

// or
if err := WaitForServer(url); err != nil {
    log.Fatalf("Site is down: %v\n", err)
}
```

 4. In som e cases, it’s sufficient just to log the error and then continue, perhaps wit h re duce d func tionality.

## Anonymous functions

Watch out for capturing variables which change.

```golang
var rmdirs []func()
for _, dir := range tempDirs() {
    os.MkdirAll(dir, 0755)
    rmdirs = append(rmdirs, func() {
        os.RemoveAll(dir) // NOTE: incorrect! need to create a new variable for dir in each loop iteration
    })
}

for _, d := range tempDirs() {
    dir := d // NOTE: necessary!
    os.MkdirAll(dir, 0755) // creates parent directories too
    rmdirs = append(rmdirs, func() {
        os.RemoveAll(dir)
    })
}
```

## Deferred Function Calls

The function and argument expressions are evaluate d when the statement is
exec ute d, but the actual cal l is deferred until the function that contains the defer st atement
has finishe d, whether nor mal ly, by exe cut ing a retur n st atement or fal ling off the end, or
abnormal ly, by panicking .
Any number of cal ls may be defer red; the y are exe cut ed in the re verse of the order in which the y were defer red.

```golang
func bigSlowOperation() {
    defer trace("bigSlowOperation")() // don't forget the extra parentheses
    // ...lots of work...
    time.Sleep(10 * time.Second) // simulate slow operation by sleeping
}

func trace(msg string) func() {
    start := time.Now()
    log.Printf("enter %s", msg)
    return func() { log.Printf("exit %s (%s)", msg, time.Since(start)) }
}
```

Deferred functions run af ter return statements have updated the function’s result var iables.
Becaus e an anonymou s func tion can access its enclosing function’s var iables, including named
resu lts, a defer red anony mou s func tion can obs erve the function’s results.

```golang
func double(x int) (result int) {
    defer func() { fmt.Printf("double(%d) = %d\n", x, result) }()
    return x + x
}

_ = double(4)
// Output:
// "double(4) = 8"
```

A defer red anony mou s func tion can even change the values that the enclosing function returns to its cal ler.

```golang
func triple(x int) (result int) {
    defer func() { result += x }()
    return double(x)
}
fmt.Println(triple(4)) // "12"
```

Becaus e defer red functions aren’t exe cut ed unt i l the ver y end of a function’s exe cut ion, 
a defer st atement in a loop des erves ext ra scrutiny.
```golang
for _, filename := range filenames {
    f, err := os.Open(filename)
    if err != nil {
        return err
    }
    defer f.Close() // NOTE: risky; could run out of file descriptors
    // ...process f...
}
```
One solut ion is to mov e the loop body, including the defer st atement, int o anot her function that is cal le d on each iterat ion.
```golang
for _, filename := range filenames {
    if err := doFile(filename); err != nil {
        return err
    }
}

func doFile(filename string) error {
    f, err := os.Open(filename)
    if err != nil {
        return err
    }
    defer f.Close()
    // ...process f...
}
```

## Panic

During a typic al panic, nor mal exec ution stops, all defer red function cal ls in that goroutine are
exec ute d, and the program crashes wit h a log message.