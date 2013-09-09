<?php

function assertFailure($script, $line, $message) {
    print "Assertion failed: $script on line: $line\n";
}

assert_options(ASSERT_ACTIVE,   true);
assert_options(ASSERT_BAIL,     true);
assert_options(ASSERT_WARNING,  false);
assert_options(ASSERT_CALLBACK, 'assertFailure');

function updatePageExpectErr($page) {
    global $service;
    try {
        $service->updatePage($page);
        print "updatePage allowed invalid page\n";
        exit(1);
    }
    catch (BarristerRpcException $e) {
        assert($e->getCode() === -32602);
    }
}

$path = $_ENV["BARRISTER_PHP"];
include_once("$path/barrister.php");

$barrister = new Barrister();
$client    = $barrister->httpClient("http://localhost:8080/cgi-bin/server.php");
//$client    = $barrister->httpClient("http://localhost:7667/content");
$service   = $client->proxy("ContentService");

$invalid_add_page = array(
    // use an int for authorId
    array(1, "title", "body", "sports"),
    // pass a null title
    array("author-1", null, "body", "sports"),
    // pass a float for body
    array("author-1", "title", 32.3, "sports"),
    // pass a bool for category
    array("author-1", "title", "body", true),
    // pass an invalid enum value
    array("author-1", "title", "body", "op-ed")
);

foreach ($invalid_add_page as $i=>$page_data) {
    try {
        $service->addPage($page_data[0], $page_data[1], $page_data[2], $page_data[3]);
        print "addPage allowed invalid data\n";
        exit(1);
    }
    catch (BarristerRpcException $e) {
        // -32602 is the standard JSON-RPC error code for
        // "invalid params", which Barrister uses if types are invalid
        assert($e->getCode() === -32602);
    }
}
print "Test 1 - Passed\n";


//
// Test 2 - Create a page, then test getPage/updatePage cases
//

$pageId = $service->addPage("author-1", "title", "body", "sports");
$page   = $service->getPage($pageId);
assert($page !== null);

$page->title = "new title";
$page->publishTime = time() * 1000;
$version = $service->updatePage($page);
assert($version === 2);

$page2 = $service->getPage($pageId);
assert($page2->title === $page->title);
assert($page2->publishTime === $page->publishTime);

print "Test 2 - Passed\n";

//
// Test 3 - Test updatePage type validation
//

$page = $page2;

// Remove required fields one at a time and verify that updatePage rejects request
$required_fields = array("id", "createdTime", "updatedTime", "version", "body", "title");
foreach ($required_fields as $i=>$field) {
    $page_copy = clone $page;
    unset($page_copy->$field);
    updatePageExpectErr($page_copy);
}

// Try sending a struct with an extra field
$page_copy = clone $page;
$page_copy->unknown = "hi";
updatePageExpectErr($page_copy);

// Try sending an array with an invalid element type
$page_copy = clone $page;
$page_copy->tags = array("good", "ok", 1);
updatePageExpectErr($page_copy);

// Try a valid array
$page_copy = clone $page;
$page_copy->tags = array("good", "ok");
$version = $service->updatePage($page_copy);
assert($version === 3);

print "Test 3 - Passed\n";


//
// Test 4 - getPage / deletePage
//

// delete non-existing page
assert(false === $service->deletePage("bogus-id", $version));

// delete real page
assert(true === $service->deletePage($page->id, $version));

// get page we just deleted
assert(null === $service->getPage($page->id));

print "Test 4 - Passed\n";

?>