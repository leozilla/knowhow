Composite types
===============

## Arrays

An array is a fixe d-lengt h sequence of zero or more elements of a par tic ular typ e. Because of
their fixe d lengt h, ar rays are rarely used direc tly in Go. Slices, which can grow and shrink, are
much more versatile.

```golang
var a [3]int         // array of 3 integers
fmt.Println(a[0])    // print the first element
fmt.Println(a[len(a)-1]) // print the last element, a[2]

var q [3]int = [3]int{1, 2, 3}
var r [3]int = [3]int{1, 2}  // or just: r := [...]int{1, 2, 3}
fmt.Println(r[2]) // "0"

// defines an array r with 100 elements, all zero except for the last, which has value −1
r := [...]int{99: -1}
```

Wh en a function is cal le d, a copy of each argument value is assig ned to the cor responding
parameter variable, so the function receives a copy, not the original. Passing large arrays in
this way can be inefficient, and any changes that the function makes to array elements affe ct
on ly the copy, not the original. In this regard, Go tre ats arrays like any other typ e, but this
behavior is dif ferent fro m languages that imp licitly pass arrays by reference.

```golang
// Zeroing out the array
func zero(ptr *[32]byte) {
    for i := range ptr {
        ptr[i] = 0
    }
}

// or better
func zero(ptr *[32]byte) {
    *ptr = [32]byte{}
}
```

## Slices

A slice has three components: a point er, a lengt h, and a cap acity.

```golang
// slice definition (size is not defined)
// This imp licitly cre ates an array variable of the rig ht size and yields a slice that points to it.
s := []int{0, 1, 2, 3, 4, 5} 
```

```golang
months := [...]string{1: "January", /* ... */, 12: "December"} // array defintion (size is defined)

Q2 := months[4:7]
summer := months[6:9]
fmt.Println(Q2)     // ["April" "May" "June"]
fmt.Println(summer) // ["June" "July" "August"]
```
Slicing beyon d cap(s) caus es a panic, but slicing beyon d len(s) extends the slice, so the resu lt may be lon g er than the original:
```golang
fmt.Println(summer[:20]) // panic: out of range
endlessSummer := summer[:5] // extend a slice (within capacity)
fmt.Println(endlessSummer) // "[June July August September October]"
```

Un like arrays, slices are not comparable, so we cannot use == to test whether two slices cont ain the same elements. 
The stand ard librar y prov ides the hig hly opt imize d bytes.Equal func tion for comparing two slices of bytes ( []byte ), 
but for other typ es of slice, we must do the comp arison ourselves.
The only legal slice comp arison is against nil.

If you need to test whether a slice is emp ty, use len(s) == 0 , not s == nil.

The bui lt-in function `make` creates a slice of a specified element typ e, lengt h, and cap acity.
```golang
make([]T, len)
make([]T, len, cap) // same as make([]T, cap)[:len]
```

Un der the hood, make creates an unnamed array variable and retur ns a slice of it; the array is
accessible only through the retur ned slice. In the firs t form, the slice is a vie w of the ent ire
ar ray. In the secon d, the slice is a vie w of only the array’s firs t len elements, but its cap acity
includes the ent ire array. The addition al elements are set aside for fut ure growt h.

The bui lt-in `append` func tion app end s it ems to slices.
Usu ally we don’t know whether a given cal l to append wi l l caus e a reallocat ion,
so we can’t assume that the original slice refers to the same array as the resulting slice, nor that
it refers to a dif ferent one.

```golang
var x []int
x = append(x, 1)
x = append(x, 2, 3)
x = append(x, 4, 5, 6)
x = append(x, x...) // append the slice x
fmt.Println(x)      // "[1 2 3 4 5 6 1 2 3 4 5 6]"
```

Use the built-in function `copy`, which copies elements fro m on e slice to another of the same type.

## Maps

```golang
// The bui lt-in function `make` can be used to cre ate a map.
ages := make(map[string]int) // mapping from strings to ints

// or via map literal
ages := map[string]int{
    "alice": 31,
    "charlie": 34,
}
// which is equivalent to
ages := make(map[string]int)
ages["alice"] = 31
ages["charlie"] = 34
```

The order of map iterat ion is uns pecified, and dif ferent imp lementation s mig ht use a dif ferent hash function, 
leading to a dif ferent order ing.

Most operat ions on maps, including looku p, delete , len , and range lo ops, are safe to perform on a nil map reference, 
since it beh aves like an emp ty map. But storing to a nil map caus es a panic.

```golang
// check for key existence
if age, ok := ages["bob"]; !ok { /* ... */ }
```

As wit h slices, maps cannot be compare d to each other ; the only legal comp arison is wit h nil.

Sometimes we need a map or set whose keys are slices, but because a map’s keys must be com-
parable, this cannot be express ed direc tly. How ever, it can be don e in two steps. First we
define a helper function k that maps each key to a str ing , with the pro per ty that k(x) == k(y)
if and only if we con sider x and y equivalent. Then we cre ate a map whose keys are str ings,
apply ing the helper function to each key before we access the map.

```golang
var m = make(map[string]int)

func k(list []string) string { return fmt.Sprintf("%q", list) }
func Add(list []string) { m[k(list)]++ }
func Count(list []string) int { return m[k(list)] }
```

```golang
var m = make(map[string]bool)     // set of strings
seen := make(map[string]struct{}) // set of strings
```

## Structs

```golang
var employeeOfTheMonth *Employee = &dilbert
employeeOfTheMonth.Position += " (proactive team player)"

// the last statement is equivalent to
(*employeeOfTheMonth).Position += " (proactive team player)"
```

```golang
func EmployeeByID(id int) *Employee { /* ... */ }

fmt.Println(EmployeeByID(dilbert.ManagerID).Position) // "Pointy-haired boss"

id := dilbert.ID
EmployeeByID(id).Salary = 0 // fired for... no real reason
```

```golang
type Employee struct {
    ID            int
    Name, Address string
    DoB           time.Time
    Position      string
    Salary        int
    ManagerID     int
}
```

Field order is sig nificant to typ e identity. Had we als o combined the declarat ion of the Posi-
tion field (also a str ing), or int erc hange d Name and Address , we wou ld be defining a dif ferent
st ruc t type. Typic ally we only com bine the declarat ions of rel ate d fields.

The zero value for a str uct is composed of the zero values of each of its fields. It is usu ally
desirable that the zero value be a natural or sensible defau lt. For example, in bytes.Buffer ,
the initial value of the str uct is a ready-to-use emp ty buf fer, and the zero value of sync.Mutex ,
which we’ll see in Chapt er 9, is a ready-to-use unlocke d mu tex.

### Struct Literals

```golang
type Point struct{ X, Y int }

// listing all fields in exact order -> fragile
p := Point{1, 2}

// listing some or all of the fields by name
anim := gif.GIF{LoopCount: nframes}
```

Fo r efficiency, larger str uct typ es are usu ally passed to or retur ned fro m func tions indirec tly using a point er and 
this is required if the function must modif y its argument, since in a cal l-by-value langu age li ke Go, 
the cal le d func tion receives only a copy of an argument, not a reference to the original argument.

```golang
// commonly used, create struct and reference it by pointer
pp := &Point{1, 2}

// equivalent to
pp := new(Point)
*pp = Point{1, 2}
```

### Struct Embedding and Anonymous Fields

```golang
type Point struct {
    X, Y int
}
type Circle struct {
    Center Point
    Radius int
}
type Wheel struct {
    Circle Circle
    Spokes int
}

var w Wheel
w.Circle.Center.X = 8
w.Circle.Center.Y = 8
w.Circle.Radius = 5
w.Spokes = 20
```

Usage with anonymous fields.
```golang
type Circle struct {
    Point
    Radius int
}
type Wheel struct {
    Circle
    Spokes int
}

var w Wheel
w.X = 8         // equivalent to w.Circle.Point.X = 8
w.Y = 8
w.Radius = 5
w.Spokes = 20
```

## JSON

Mars haling uses the Go str uct field names as the field names for the JSON obj e cts (through reflec tion).

```golang
data, err := json.Marshal(movies)
data, err := json.MarshalIndent(movies, "", "") // pretty print
```

Matching pro cess that ass oci ates JSON names wit h Go str uct names dur ing
unmarsh aling is cas e-insensit ive , so it’s only necessary to use a field tag when there’s an under-
score in the JSON name but not in the Go name.