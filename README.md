
# TXTED

`txted` is a command-line utility developed using Java. The tool is designed to read a text file and perform various operations based on user-specified parameters. The supported operations are:

-   Exclude lines containing a specified substring
-   Perform case sensetive or case insensetive operations
-   Skip even or odd numbered lines
-   Append a suffix to each line
-   Reverse the order of lines
-   Write output to a new file or same file

## Usage

To use `txted`, run the following command:

```bash
java txted [options] filename
``` 

Here, `filename` is the name of the file to operate on, and the `[options]` are the operations to perform. You can use any combination of options.

## Options

The following options are available:

-   `-e [substring]`: exclude lines containing the specified substring
-   `-f`: make the changes inplace
-   `-i`: case insensetive
-   `-x [suffix]`: append the specified suffix to each line
-   `-s [0 or 1]`: 0 for skip even & 1 for skip odd numbered lines
-   `-r`: reverse the order of lines
-   `-n [number]`: add line number to each line with specified number of digits

For example, to exclude all lines containing the substring "example", and skip even numbered lines of the file `input.txt`, run the following command:

```bash
java txted -e example -s -0 input.txt
``` 

## Contributing

If you find any issues or have any suggestions for the tool, feel free to open an issue or submit a pull request.

## License

This project is licensed under the MIT License. See the [LICENSE](https://github.com/aamirali-dev/txted/blob/master/LICENCE) file for details.
