Program structure
=================

## Variables

In Go there is no such thing as an uninitialized variable.
If the expression is omitted, the initial value is the zero value for the type, which is 0 for numbers, false for booleans,
"" for strings, and nil for interfaces and reference typ es (slice, point er, map, channel, function).
The zero value of an aggregate type like an array or a struct has the zero value of all of its elements or fields.

Package-le vel variables are initialized before main begins (§2.6.2),
and local variables are initialized as their declarations are encountered during function execution.

__Short Variable Declarations__
```golang
anim := gif.GIF{LoopCount: nframes}
freq := rand.Float64() * 3.0
t := 0.0
```

## Pointers

```golang
x := 1           
p := &x           // p, of type *int, points to x    
fmt.Println(*p)   // "1"
*p = 2            // equivalent to x = 2
fmt.Println(x)    // "2"
``` 

```golang
var p = f()
func f() *int {
    v := 1
    return &v
}

// Each cal l of f returns a distinc t value
fmt.Println(f() == f()) // "false"
``` 

### The new function

```golang
p := new(int)    // p, of type *int, points to an unnamed int variable
fmt.Println(*p)  // "0"
*p = 2           // sets the unnamed int to 2 
fmt.Println(*p)  // "2"
```

```golang
// both functions have the identical behaviour
func newInt() *int {
    return new(int)
}

func newInt() *int {
    var dummy int
    return &dummy
}
```

Each call to new returns a distinct variable wit h a unique address:

```golang
p := new(int)
q := new(int)
fmt.Println(p == q) // "false"
```

There is one exception to this rule: two variables whose type carries no information and is
therefore of size zero, such as struct{} or [0]int , may, dep ending on the implementation, have the same address.

### Lifetime of Variables

The lifetime of a package-level variable is the entire execution of the program.

A compiler may cho ose to allocate local variables on the heap or on the stack but, perhaps surprisingly,
this choice is not deter mined by whether var or new was used to declare the variable.

```golang
var global *int

// x escapes f()
func f() {
    var x int
    x = 1
    global = &x
}
```

Each variable that escapes requires an extra memory allocation.

Keeping unnecessary point ers to short-lived objects wit hin long-live d objects, especially global variables, 
will prevent the garb age col lec tor from reclaiming the short-lived objects.

### Assignability

```golang
medals := []string{"gold", "silver", "bronze"}
// same as
medals[0] = "gold"
medals[1] = "silver"
medals[2] = "bronze"
```

### Type Declarations

```golang
type Celsius float64
type Fahrenheit float64
const (
    AbsoluteZeroC Celsius = -273.15
    FreezingC     Celsius = 0
    BoilingC      Celsius = 100
)
func CToF(c Celsius) Fahrenheit { return Fahrenheit(c*9/5 + 32) }
func FToC(f Fahrenheit) Celsius { return Celsius((f - 32) * 5 / 9) }
```

Conv ersions are als o al lowe d between numer ic types, and bet ween str ing and som e slice typ es,
as we will see in the next chapt er. These conversions may change the represent ation of the
value. For ins tance, converting a floating-p oint number to an int eger dis cards any frac tional
part, and converting a str ing to a []byte slice allo cates a copy of the str ing dat a. In any cas e, a
conv ersion never fails at run time.

### Package Initialization

Package initializat ion beg ins by initializing package-le vel var iables in the order in which the y
are declare d, except that dep endencies are res olved firs t.

`init` func tions can’t be cal le d or reference d, but other wis e they are nor mal functions.
Wi thin each file, init func tions are aut omat ical ly exe cut ed when the program starts, in the
order in which the y are declare d.

### Scope

```golang
f, err := os.Open(fname)
if err != nil {
    return err
}
f.ReadByte()
f.Close()

// or if f is not used
if _, err := os.Open(fname); err != nil {
    return err
}
```