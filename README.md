CWiid & Java Native Access (JNA)
================================
This tutorial briefly shows how to use the CWiid library in Scala through the JNA library.

### Libraries

The installation of the CWiid library is explained on [their website](http://abstrakraft.org/cwiid).
* CWiid 0.6.00
* JNA 4.0.0
* JNAerator 0.12-SNAPSHOT 20130727

### Mapping between C and Java

In order to use the CWiid library, we will have to map the C library.
The mapping will be done using JNAerator and it will generate a JAR file.
The following figure shows what configuration was used:

![JNAerator](https://github.com/ThmX/cwiid-jna/raw/master/jnaerator.png "JNAerator")

`cwiid.h` was tweaked to avoid having to import `bluetooth.h` which was producing errors.

```c
// #include <bluetooth/bluetooth.h>	/* bdaddr_t */
typedef struct {
	uint8_t b[6];
} __attribute__((packed)) bdaddr_t;
```

Finally, the `cwiid.jar` will be generated.

## Example

#### Scala

[scala source](https://github.com/ThmX/cwiid-jna/blob/master/cwiid.scala)

Compile & run the code above using the following commands, don't forget to place the libraries in the same directory.

	scalac -cp jna.jar:jnaerator.jar:cwiid.jar cwiid.scala
	scala -cp .:jna.jar:jnaerator.jar:cwiid.jar CWiid


## References

* [CWiid](http://abstrakraft.org/cwiid)
* [Java Native Access (JNA)](https://github.com/twall/jna)
* [JNAerator](https://code.google.com/p/jnaerator/)

## License

The MIT License (MIT)

Copyright (c) 2014 Thomas Denoréaz

Permission is hereby granted, free of charge, to any person obtaining a copy of
this software and associated documentation files (the "Software"), to deal in
the Software without restriction, including without limitation the rights to
use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
the Software, and to permit persons to whom the Software is furnished to do so,
subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.