# Unpacker

[![](https://jitpack.io/v/stevenyomi/unpacker.svg)](https://jitpack.io/#stevenyomi/unpacker)

A Java library to unpack JavaScript code compressed by [packer](http://dean.edwards.name/packer/).

Source code of packer can be found [here](https://github.com/evanw/packer/blob/master/packer.js).

Implementations of the classes are meant to be efficient, so be careful of edge cases.

## Usage

```
Unpacker.unpack(script[, left, right])
```

- **script** can be either `String` or `ProgressiveParser`
- specify **left** and **right** to unpack only the data between them

Note: single quotes `\'` in the data will be replaced with double quotes `"`.
