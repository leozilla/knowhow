Golang Best Practices 2016
==========================

Source: https://peter.bourgon.org/go-best-practices-2016

 * Defer to Andrew Gerrands' naming conventions: https://talks.golang.org/2014/names.slide#1
 * Only func main has the right to decide which flags are available to the user.
 * Use struct literal initialization to avoid invalid intermediate state. Inline struct declarations where possible.
 * Avoid nil checks via default no-op implementations.
 * Make the zero value useful, especially in config objects
 * Make dependencies explicit! (Loggers are dependencies)
 * Use many small interfaces to model dependencies.

## 1. Defer to Andrew Gerrands' naming conventions

[Go naming convention](naming.md)

## 2. Only func main has the right to decide which flags are available to the user

## 3. Use struct literal initialization to avoid invalid intermediate state. Inline struct declarations where possible.

```golang
// Don't do this.
foo, err := newFoo(
    *fooKey,
    bar,
    100 * time.Millisecond,
    nil,
)

// Don't do this.
cfg := fooConfig{}
cfg.Bar = bar
cfg.Period = 100 * time.Millisecond
cfg.Output = nil

foo, err := newFoo(*fooKey, cfg)

// this is better
cfg := fooConfig{
    Bar:    bar,
    Period: 100 * time.Millisecond,
    Output: nil,
}

foo, err := newFoo(*fooKey, cfg)
```
No statements go by where the object is in an intermediate, invalid state. 
And all of the fields are nicely delimited and indented, mirroring the fooConfig definition.

## 4. Avoid nil checks via default no-op implementations.

```golang
// Don't do this.
func (f *foo) process() {
    if f.Output != nil {
        fmt.Fprintf(f.Output, "start\n")
    }
    // ...
}

// use a no/op default for Output
func (f *foo) process() {
     fmt.Fprintf(f.Output, "start\n")
     // ...
}
func newFoo(..., cfg fooConfig) *foo {
    if cfg.Output == nil {
        cfg.Output = ioutil.Discard
    }
    // ...
}
```

## 5. Make the zero value useful, especially in config objects

```golang
func newFoo(..., cfg fooConfig) *foo {
    if cfg.Output == nil {
        cfg.Output = ioutil.Discard
    }
    // ...
}
```

## 6. Make dependencies explicit!

```golang
func (f *foo) process() {
    fmt.Fprintf(f.Output, "start\n")
    result := f.Bar.compute()
    log.Printf("bar: %v", result) // Whoops!
    // ...
}
```
Becomes.
```golang
func (f *foo) process() {
    fmt.Fprintf(f.Output, "start\n")
    result := f.Bar.compute()
    f.Logger.Printf("bar: %v", result) // Better.
    // ...
}
```
Loggers are dependencies, just like references to other components, database handles, commandline flags, etc.