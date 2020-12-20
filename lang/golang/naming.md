Go naming convention
====================

https://talks.golang.org/2014/names.slide#1

A good name is:

* Consistent (easy to guess)
* Short (easy to type)
* Accurate (easy to understand)

The greater the distance between a name's declaration and its uses, the longer the name should be.

### Use MixedCase

Names in Go should use MixedCase.

(Don't use names_with_underscores.)

Acronyms should be all capitals, as in `ServeHTTP` and `IDProcessor`.

### Local variables

Keep them short; long names obscure what the code does.

Common variable/type combinations may use really short names:

Prefer i to index.
Prefer r to reader.
Prefer b to buffer.

Avoid redundant names, given their context:

Prefer count to runeCount inside a function named RuneCount.
Prefer ok to keyInMap in the statement
```golang
v, ok := m[k]
```
Longer names may help in long functions, or functions with many local variables.
(But often this just means you should refactor.)

Bad
```golang
func RuneCount(buffer []byte) int {
    runeCount := 0
    for index := 0; index < len(buffer); {
        if buffer[index] < RuneSelf {
            index++
        } else {
            _, size := DecodeRune(buffer[index:])
            index += size
        }
        runeCount++
    }
    return runeCount
}
```
Good
```golang
func RuneCount(b []byte) int {
    count := 0
    for i := 0; i < len(b); {
        if b[i] < RuneSelf {
            i++
        } else {
            _, n := DecodeRune(b[i:])
            i += n
        }
        count++
    }
    return count
}
```

### Parameters

Function parameters are like local variables, but they also serve as documentation.

Where the types are descriptive, they should be short:
```golang
func AfterFunc(d Duration, f func()) *Timer

func Escape(w io.Writer, s []byte)
```
Where the types are more ambiguous, the names may provide documentation:
```golang
func Unix(sec, nsec int64) Time

func HasPrefix(s, prefix []byte) bool
```

### Return values

Return values on exported functions should only be named for documentation purposes.

These are good examples of named return values:
```golang
func Copy(dst Writer, src Reader) (written int64, err error)

func ScanBytes(data []byte, atEOF bool) (advance int, token []byte, err error)
```

### Receivers

Receivers are a special kind of argument.

By convention, they are one or two characters that reflect the receiver type, because they typically appear on almost every line:
```golang
func (b *Buffer) Read(p []byte) (n int, err error)

func (sh serverHandler) ServeHTTP(rw ResponseWriter, req *Request)

func (r Rectangle) Size() Point
```
Receiver names should be consistent across a type's methods.
(Don't use r in one method and rdr in another.)

### Exported package-level names

Exported names are qualified by their package names.

Remember this when naming exported variables, constants, functions, and types.

That's why we have bytes.Buffer and strings.Reader, not bytes.ByteBuffer and strings.StringReader.

### Interface Types

Interfaces that specify just one method are usually just that function name with 'er' appended to it.
```golang
type Reader interface {
    Read(p []byte) (n int, err error)
}
```
Sometimes the result isn't correct English, but we do it anyway:
```golang
type Execer interface {
    Exec(query string, args []Value) (Result, error)
}
```
Sometimes we use English to make it nicer:
```golang
type ByteReader interface {
    ReadByte() (c byte, err error)
}
```
When an interface includes multiple methods, choose a name that accurately describes its purpose
(examples: net.Conn, http.ResponseWriter, io.ReadWriter).

### Errors

Error types should be of the form FooError:
```golang
type ExitError struct {
    ...
}
```
Error values should be of the form ErrFoo:
```golang
var ErrFormat = errors.New("image: unknown format")
```

### Import paths

The last component of a package path should be the same as the package name.
```golang
"compress/gzip" // package gzip
```
Avoid stutter in repository and package paths:
```golang
"code.google.com/p/goauth2/oauth2" // bad; my fault
```
For libraries, it often works to put the package code in the repo root:
```golang
"github.com/golang/oauth2" // package oauth2
```
Also avoid upper case letters (not all file systems are case sensitive).

### Packages

Choose package names that lend meaning to the names they export.

Steer clear of util, common, and the like.