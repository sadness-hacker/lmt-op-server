#!/usr/bin/perl -w
use strict;
use DBI;
use LWP;
use Digest::MD5 qw/md5 md5_hex md5_base64/;
use POSIX qw/strftime/;
use JSON;

my $j = '{"ducx":"悲情黑客","m":{"success":"true"}}';
my $json = decode_json($j);

print $json->{'ducx'} . "\n";
print $json->{'m'}->{'success'} . "\n";
