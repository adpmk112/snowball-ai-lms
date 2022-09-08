$(document).ready(function () {
  if ( $.fn.DataTable.isDataTable( '#datatable' ) ) {
    $( '#datatable' ).DataTable().destroy();
  }
  $( '#datatable' ).dataTable( {
    "bSort": false,
    "bDestroy": true
  } );

  if ( $.fn.DataTable.isDataTable( '#datatable1' ) ) {
    $( '#datatable1' ).DataTable().destroy();
  }
  $( '#datatable1' ).dataTable( {
    "bSort": false,
    "bDestroy": true
  } );
  if ( $.fn.DataTable.isDataTable( '#datatable2' ) ) {
    $( '#datatable2' ).DataTable().destroy();
  }
  $( '#datatable2' ).dataTable( {
    "bSort": false,
    "bDestroy": true
  } );
  if ( $.fn.DataTable.isDataTable( '#datatable3' ) ) {
    $( '#datatable3' ).DataTable().destroy();
  }
  $( '#datatable3' ).dataTable( {
    "bSort": false,
    "bDestroy": true
  } );
  if ( $.fn.DataTable.isDataTable( '#datatable4' ) ) {
    $( '#datatable4' ).DataTable().destroy();
  }
  $( '#datatable4' ).dataTable( {
    "bSort": false,
    "bDestroy": true
  } );
  if ( $.fn.DataTable.isDataTable( '#datatable5' ) ) {
    $( '#datatable5' ).DataTable().destroy();
  }
  $( '#datatable5' ).dataTable( {
    "bSort": false,
    "bDestroy": true
  } );

});

