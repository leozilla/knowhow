run forks but console doesn't, so simply sbt -Dfoo=bar console

If need be you can set it:

in the sbt shell with eval sys.props("foo") = "bar"
in the REPL (console) with sys.props("foo") = "bar"
in build.sbt with val setFoo = sys.props("foo") = "bar"