describe('Pigeon Dashboard', () => {
  // Mock authentication token and API responses before each test
  beforeEach(() => {
    // Stub window methods to handle localStorage
    cy.stub(window, 'localStorage').as('localStorage');

    // Intercept and mock authentication and API calls
    cy.intercept('GET', '**/api/pigeons/user', {
      statusCode: 200,
      body: [
        { numeroBague: 'm123', couleur: 'Blue', age: 2 },
        { numeroBague: 'f456', couleur: 'White', age: 3 }
      ]
    }).as('getPigeons');

    cy.intercept('POST', '**/api/pigeons/add', (req) => {
      req.reply({
        statusCode: 200,
        body: { message: 'Pigeon added successfully' }
      });
    }).as('addPigeon');

    // Simulate authentication by setting token in localStorage
    cy.visit('/dashboard', {
      onBeforeLoad(win) {
        win.localStorage.setItem('authToken', 'test-token');
      }
    });
  });

  // it('should load existing pigeons', () => {
  //   // Ensure the page is fully loaded
  //   cy.get('.list-group').should('be.visible');
  //
  //   // Check that pigeons are displayed
  //   cy.get('.list-group-item').should('have.length.gt', 0);
  //   cy.contains('.list-group-item', 'm123').should('be.visible');
  // });

  it('should validate form inputs', () => {
    // Focus and blur each input to trigger validation
    cy.get('input[formControlName="numeroBague"]')
      .focus()
      .blur();
    cy.contains('.text-danger', 'Numero Bague must start with')
      .should('be.visible');

    cy.get('input[formControlName="couleur"]')
      .focus()
      .blur();
    cy.contains('.text-danger', 'Couleur is required')
      .should('be.visible');

    cy.get('input[formControlName="age"]')
      .focus()
      .blur();
    cy.contains('.text-danger', 'Age is required')
      .should('be.visible');
  });

  it('should add a new pigeon successfully', () => {
    // Fill out the form with valid data
    cy.get('input[formControlName="numeroBague"]').type('m789');
    cy.get('input[formControlName="couleur"]').type('Gray');
    cy.get('input[formControlName="age"]').type('4');

    // Submit the form
    cy.get('button[type="submit"]').click();

    // Check success message appears
    cy.contains('.alert-success', 'Pigeon added successfully!')
      .should('be.visible');
  });


  it('should show error message for failed pigeon addition', () => {
    // Mock a server error response
    cy.intercept('POST', '**/api/pigeons/add', {
      statusCode: 500,
      body: { message: 'Server error' }
    }).as('addPigeonFailed');

    // Fill out the form
    cy.get('input[formControlName="numeroBague"]').type('m789');
    cy.get('input[formControlName="couleur"]').type('Gray');
    cy.get('input[formControlName="age"]').type('4');

    // Submit the form
    cy.get('button[type="submit"]').click();

    // Check error message appears
    cy.contains('.alert-danger', 'Failed to add pigeon')
      .should('be.visible');
  });
});
