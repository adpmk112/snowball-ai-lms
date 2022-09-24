$(document).ready(function () {
  if ( $.fn.DataTable.isDataTable( '#datatable' ) ) {
    $( '#datatable' ).DataTable().destroy();
  }
  $( '#datatable' ).dataTable( {    
    "bDestroy": true
  } );

  if ( $.fn.DataTable.isDataTable( '#datatable1' ) ) {
    $( '#datatable1' ).DataTable().destroy();
  }
  $( '#datatable1' ).dataTable( {   
    "bDestroy": true
  } );
  if ( $.fn.DataTable.isDataTable( '#datatable2' ) ) {
    $( '#datatable2' ).DataTable().destroy();
  }
  $( '#datatable2' ).dataTable( {    
    "bDestroy": true
  } );
  if ( $.fn.DataTable.isDataTable( '#datatable3' ) ) {
    $( '#datatable3' ).DataTable().destroy();
  }
  $( '#datatable3' ).dataTable( {    
    "bDestroy": true
  } );
  if ( $.fn.DataTable.isDataTable( '#datatable4' ) ) {
    $( '#datatable4' ).DataTable().destroy();
  }
  $( '#datatable4' ).dataTable( {
      "bDestroy": true
  } );
  if ( $.fn.DataTable.isDataTable( '#datatable5' ) ) {
    $( '#datatable5' ).DataTable().destroy();
  }
  $( '#datatable5' ).dataTable( {    
    "bDestroy": true
  } );

  $('#assignmentDataTable').dataTable({ //For teacher Assignment Data table
    
  })

});

