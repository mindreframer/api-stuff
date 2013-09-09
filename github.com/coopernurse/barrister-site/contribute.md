---
layout: default
title: Barrister RPC - Contribute
---

## Contribute

There are several ways you can help out the project.

### Promote

Open source projects live and die based on the vibrancy of their communities.  One of the most
important ways you can assist the project is simply by talking about it.

* Write a blog article about Barrister
* Tweet about the project
* Demo it at a developer meetup

If you write an article or do a presentation, please send a note about it to the 
[mailing list](https://groups.google.com/forum/#!forum/barrister-rpc).
Once we have enough articles we'll aggregate them on a page on this site.

### Write docs and tutorials

You can [fork this web site](https://github.com/coopernurse/barrister-site) and add content.
The site uses [Jekyll](https://github.com/mojombo/jekyll) so please install that to test your changes
locally.

I'm currently using it to generate a more *traditional* web site comprised of static pages, as 
opposed to a blog type format.  But that may change as time goes on.  If folks want more temporal
content we can start using Jekyll's blog features too.

Please use Markdown for all content.

### Fix a Bug

The [download page](download.html) lists the various GitHub projects related to Barrister.  Please
browse the issue list and take a stab at fixing something on a fork.  Pull requests are fine.

If the fix could impact backwards compatibility, please discuss your proposed fix 
[on the mailing list](https://groups.google.com/forum/#!forum/barrister-rpc) before making the
change so that we can all agree on the solution.

### Write a language binding

We'd love to have bindings for your favorite language.  Please read the 
[binding dev guide](binding.html) for more information.  Once your binding gets far enough along
we'll want to integrate it into the conformance test harness that our Jenkins server runs, which
will give users confidence that your binding will interoperate with other Barrister language
bindings.
