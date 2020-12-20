Data types
==========

## Strings

The bui lt-in len func tion retur ns the number of bytes (not runes) in a str ing , and the in dex
op erat ion s[i] retr ieves the i-t h byte of str ing s , where 0 ≤ i < len(s) .

```golang
s := "hello, world"    
fmt.Println(len(s))      // "12"
fmt.Println(s[0], s[7])  // "104 119"('h' and 'w') 

c := s[len(s)] // panic: index out of range

fmt.Println(s[0:5]) // "hello"
fmt.Println(s[:5]) // "hello"
fmt.Println(s[7:]) // "world"
fmt.Println(s[:]) // "hello, world" 
```

St ring values are immut able: the byte sequence cont ained in a str ing value can never be
ch ange d, thoug h of cours e we can assig n a new value to a str ing variab le.

A raw str ing litera l is writt en `...` , using backquotes ins tead of dou ble quotes. Wit hin a raw
st ring lit eral, no escap e sequences are pro cessed; the contents are taken literal ly, including
backsl ashes and newlines, so a raw str ing lit eral may spread over several lines in the program
source.

Raw str ing lit erals are a convenient way to write regu lar expressions, which tend to have lots of
backsl ashes. They are als o us eful for HTML templ ates, JSON lit erals, command usage mes-
sages, and the like, which often extend over multiple lines.

### Strings and Byte Slices

Fo ur stand ard packages are par tic ularly imp ortant for manipu lat ing str ings: bytes , strings ,
strconv , and unicode.

The bytes package has similar functions for manipu lat ing slices of bytes, of typ e []byte ,
which share som e prop erties wit h st rings. Because str ings are immut able, bui lding up str ings
incrementally can invo l ve a lot of allocat ion and copying . In such cas es, it’s more efficient to
us e the bytes.Buffer type, which we’ll show in a mom ent.

```golang
// Strings can be converted to byte slices and back again
s := "abc"
b := []byte(s)
s2 := string(b)
```

Conceptu ally, the []byte(s) conv ersion allocates a new byte array holding a copy of the bytes
of s , and yields a slice that references the ent irety of that array. An opt imizing compi ler may
be able to avoid the allocat ion and copying in som e cases, but in general copy ing is required to
ensure that the bytes of s remain unch ange d even if those of b are subsequently modified. The
conv ersion fro m byte slice back to str ing wit h string(b) also makes a copy, to ens ure
immut abi lit y of the resulting str ing s2 .
To avoid conv ersions and unnecessary memor y al location, many of the utilit y func tions in the
bytes package direc tly paral lel their counterpar ts in the strings package.

### Conversions between Strings and Numbers

To convert an int eger to a str ing , on e opt ion is to use fmt.Sprintf ; another is to use the func-
tion strconv.Itoa (‘‘integer to ASCII’’).

To parse a str ing represent ing an int eger, use the strconv func tions Atoi or ParseInt , or
ParseUint for unsig ned int egers.

## Constants

Cons tants are expressions whose value is known to the compi ler and whose evaluation is guar-
ante e d to occ ur at comp ile time, not at run time. The underly ing typ e of every con stant is a
basic typ e: boole an, st ring, or number.

### The Constant Generator iota

```golang
type Weekday int
const (
    Sunday Weekday = iota
    Monday
    Tuesday
    Wednesday
    Thursday
    Friday
    Saturday
)
```